package dev.usenkonastia.processor;

import com.google.auto.service.AutoService;
import dev.usenkonastia.processor.validation.annotations.*;

import javax.annotation.processing.*;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

@AutoService(Processor.class)
@SupportedAnnotationTypes("dev.usenkonastia.processor.validation.annotations.SqlGenerator")
@SupportedSourceVersion(javax.lang.model.SourceVersion.RELEASE_21)
public class SQLGeneratorProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(SqlGenerator.class);

        for (Element element : annotatedElements) {
            if (element.getKind() == ElementKind.CLASS) {
                generateSQLGenerator(element);
            }
        }

        return true;
    }

    private void generateSQLGenerator(Element classElement) {
        String className = classElement.getSimpleName().toString();
        String packageName = processingEnv.getElementUtils().getPackageOf(classElement).toString();
        String generatorClassName = className + "SQLGenerator";

        try (PrintWriter writer = new PrintWriter(
                processingEnv.getFiler()
                        .createSourceFile(packageName + "." + generatorClassName)
                        .openWriter())) {

            // Package declaration
            writer.printf("package %s;%n%n", packageName);
            writer.println("""
            import dev.usenkonastia.processor.validation.Validator;
            
            import java.lang.reflect.Method;
            import java.util.List;
            
            """);

            // Class declaration
            writer.printf("public class %s {%n%n", generatorClassName);

            // Generate CREATE TABLE SQL
            writer.printf("""
                        public String generateCreateTableSQL() {
                            StringBuilder sql = new StringBuilder();
                    
                            sql.append("CREATE TABLE %s (\\n");
                            sql.append("    id SERIAL PRIMARY KEY,\\n");
                    """.formatted(extractTableName(classElement)));

            for (Element field : classElement.getEnclosedElements()) {
                if (field.getKind() == ElementKind.FIELD) {
                    Column column = field.getAnnotation(Column.class);
                    if (column != null) {
                        StringBuilder columnDefinition = new StringBuilder();
                        columnDefinition.append(column.name()).append(" ").append(column.type());

                        // Handle NotNull annotation
                        if (field.getAnnotation(NotNull.class) != null) {
                            columnDefinition.append(" NOT NULL");
                        }

                        // Handle StringLength annotation
                        StringLength stringLength = field.getAnnotation(StringLength.class);
                        if (stringLength != null) {
                            columnDefinition.append(" CHECK(LENGTH(").append(column.name()).append(") >= ")
                                    .append(stringLength.min()).append(" AND LENGTH(").append(column.name())
                                    .append(") <= ").append(stringLength.max()).append(")");
                        }

                        // Handle MinValue and MaxValue annotations
                        MinValue minValue = field.getAnnotation(MinValue.class);
                        MaxValue maxValue = field.getAnnotation(MaxValue.class);
                        if (minValue != null) {
                            columnDefinition.append(" CHECK(").append(column.name()).append(" >= ")
                                    .append(minValue.value()).append(")");
                        }
                        if (maxValue != null) {
                            columnDefinition.append(" CHECK(").append(column.name()).append(" <= ")
                                    .append(maxValue.value()).append(")");
                        }

                        writer.printf("        sql.append(\"    %s,\\n\");%n", columnDefinition);
                    }
                }
            }

            // Finalize CREATE TABLE SQL
            writer.println("""
                            
                                    if (sql.charAt(sql.length() - 2) == ',') {
                                        sql.delete(sql.length() - 2, sql.length());
                                    }
                            
                                    sql.append("\\n);");
                                    return sql.toString();
                                }
                            """);

            writer.printf("""
                        public String generateInsertSQL(List<?> objects) {
                            if (objects == null || objects.isEmpty()) {
                                throw new IllegalArgumentException("List of objects is null or empty.");
                            }
                    
                            for (Object object : objects) {
                                try {
                                    Validator.validate(object);
                                } catch (Exception e) {
                                    throw new IllegalArgumentException(e.getMessage());
                                }
                            }
                    
                            StringBuilder sql = new StringBuilder();
                            sql.append("INSERT INTO %s (");
                    
                            StringBuilder columnNames = new StringBuilder();
                            columnNames.append("%s");
                    
                            sql.append(columnNames).append(") VALUES ");
                    
                            for (Object object : objects) {
                                StringBuilder values = new StringBuilder();
                                values.append("(");
                    """.formatted(extractTableName(classElement), getColumnNames(classElement)));

            for (Element field : classElement.getEnclosedElements()) {
                if (field.getKind() == ElementKind.FIELD) {
                    Column column = field.getAnnotation(Column.class);
                    if (column != null) {
                        String fieldName = field.getSimpleName().toString();
                        writer.printf("""
                                
                                            try {
                                                Method getter = object.getClass().getMethod("get%s");
                                                Object value = getter.invoke(object);
                                
                                                values.append("\\'").append(value).append("\\'").append(", ");
                                            } catch (Exception e) {
                                                throw new RuntimeException("Error accessing getter for field: %s", e);
                                            }
                                """.formatted(capitalize(fieldName), fieldName));
                    }
                }
            }

            writer.println("""
                                values.delete(values.length() - 2, values.length());
                                values.append("), ");
                                sql.append(values);
                            }
                    
                            sql.delete(sql.length() - 2, sql.length());
                            sql.append(";");
                            return sql.toString();
                        }
                    }
                    """);
        } catch (IOException e) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Error generating SQLGenerator class for " + className);
        }
    }

    private String getColumnNames(Element classElement) {
        StringBuilder columnNames = new StringBuilder();
        for (Element field : classElement.getEnclosedElements()) {
            if (field.getKind() == ElementKind.FIELD) {
                Column column = field.getAnnotation(Column.class);
                if (column != null) {
                    String columnName = column.name().isEmpty() ? field.getSimpleName().toString().toLowerCase() : column.name();
                    columnNames.append(columnName).append(", ");
                }
            }
        }

        if (!columnNames.isEmpty()) {
            columnNames.delete(columnNames.length() - 2, columnNames.length());
        }
        return columnNames.toString();
    }

    private String extractTableName(Element classElement) {
        Table table = classElement.getAnnotation(Table.class);
        return (table != null && !table.name().isEmpty()) ? table.name() : classElement.getSimpleName().toString().toLowerCase();
    }

    private String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}