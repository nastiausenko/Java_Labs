package dev.usenkonastia.processor;

import com.google.auto.service.AutoService;
import dev.usenkonastia.processor.validation.annotations.*;

import javax.annotation.processing.*;
import javax.lang.model.element.*;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Annotation processor for generating SQL commands for table creation and insertion.
 * <p>
 * This processor generates an SQL generator class for each annotated class with the {@link SqlGenerator} annotation.
 * </p>
 *
 * @author Anastasiia Usenko
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes("dev.usenkonastia.processor.validation.annotations.SqlGenerator")
@SupportedSourceVersion(javax.lang.model.SourceVersion.RELEASE_17)
public class SQLGeneratorProcessor extends AbstractProcessor {

    /**
     * Processes the annotated elements and generates the SQL generator class.
     *
     * @param annotations The set of annotations found in the current round.
     * @param roundEnv The environment for the current round of annotation processing.
     * @return true if the annotations are processed successfully.
     */
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

    /**
     * Generates the SQL generator class for the annotated class element.
     *
     * @param classElement The annotated class element.
     */
    private void generateSQLGenerator(Element classElement) {
        String className = classElement.getSimpleName().toString();
        String packageName = processingEnv.getElementUtils().getPackageOf(classElement).toString();
        String generatorClassName = className + "SQLGenerator";

        try (PrintWriter writer = new PrintWriter(
                processingEnv.getFiler()
                        .createSourceFile(packageName + "." + generatorClassName)
                        .openWriter())) {

            writeHeader(writer, packageName);
            writeClassJavadoc(writer, classElement);
            writeClassDeclaration(writer, generatorClassName, classElement);

            generateColumnDefinitions(writer, classElement);

            writeGenerateCreateTableSQL(writer, classElement);
            writeGenerateInsertSQL(writer, classElement);

            createValidateObjects(writer);
            createGenerateValuesForObject(writer, classElement);
            createExtractFieldValue(writer);

        } catch (IOException e) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                    "Error generating SQLGenerator class for " + classElement.getSimpleName());
        }
    }

    private void writeHeader(PrintWriter writer, String packageName) {
        writer.printf("package %s;%n%n", packageName);

        writer.println("""
                import dev.usenkonastia.processor.validation.Validator;
                
                import java.lang.reflect.Method;
                import java.util.List;
                """);
    }

    private void writeClassJavadoc(PrintWriter writer, Element classElement) {
        writer.println("""
                /**
                 * Auto-generated class for generating SQL commands to create a table and insert records into a "%s" table.
                 * <p>
                 * This is an auto-generated class, which should not be modified directly.
                 * </p>
                 *
                 * @author Anastasiia Usenko
                 */
                """.formatted(extractTableName(classElement)));
    }

    private void writeClassDeclaration(PrintWriter writer, String generatorClassName, Element classElement) {
        writer.printf("public class %s {%n%n", generatorClassName);
        writer.printf("    private static final String TABLE_NAME = \"%s\";%n", extractTableName(classElement));
        writer.println("    private static final String PRIMARY_KEY = \"id SERIAL PRIMARY KEY\";");
    }

    private void generateColumnDefinitions(PrintWriter writer, Element classElement) {
        for (Element field : classElement.getEnclosedElements()) {
            if (field.getKind() == ElementKind.FIELD) {
                Column column = field.getAnnotation(Column.class);
                if (column != null) {
                    String columnDefinition = createColumnDefinition(field, column);
                    writer.printf("    private static final String %s_COLUMN = \"%s\";%n",
                            column.name().toUpperCase(), columnDefinition);
                }
            }
        }
        writer.println();
    }

    private String createColumnDefinition(Element field, Column column) {
        StringBuilder columnDefinition = new StringBuilder()
                .append(column.name()).append(" ").append(column.type());

        if (field.getAnnotation(NotNull.class) != null) {
            columnDefinition.append(" NOT NULL");
        }

        appendLengthConstraints(field, column, columnDefinition);
        appendValueConstraints(field, column, columnDefinition);

        return columnDefinition.toString();
    }

    private void appendLengthConstraints(Element field, Column column, StringBuilder columnDefinition) {
        StringLength stringLength = field.getAnnotation(StringLength.class);
        if (stringLength != null) {
            columnDefinition.append(" CHECK(LENGTH(").append(column.name())
                    .append(") >= ").append(stringLength.min()).append(" AND LENGTH(")
                    .append(column.name()).append(") <= ").append(stringLength.max()).append(")");
        }
    }

    private void appendValueConstraints(Element field, Column column, StringBuilder columnDefinition) {
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
    }

    private void writeGenerateCreateTableSQL(PrintWriter writer, Element classElement) {
        writer.println("""
                        /**
                         * Generates the SQL command to create the table.
                         *
                         * @return SQL command as a string
                         */
                        public String generateCreateTableSQL() {
                            StringBuilder sql = new StringBuilder()
                                    .append("CREATE TABLE ").append(TABLE_NAME).append(" (\\n")
                                    .append("    ").append(PRIMARY_KEY).append(",\\n");
                    """);

        for (Element field : classElement.getEnclosedElements()) {
            if (field.getKind() == ElementKind.FIELD) {
                Column column = field.getAnnotation(Column.class);
                if (column != null) {
                    writer.printf("        sql.append(\"    \").append(%s_COLUMN).append(\",\\n\");%n",
                            column.name().toUpperCase());
                }
            }
        }

        writer.println("""
                            if (sql.charAt(sql.length() - 2) == ',') {
                                sql.delete(sql.length() - 2, sql.length());
                            }
                            sql.append("\\n);");
                            return sql.toString();
                        }
                    """);
    }

    private void writeGenerateInsertSQL(PrintWriter writer, Element classElement) {
        writer.println("""
                        /**
                         * Generates the SQL command to insert a list of objects into the table.
                         *
                         * @param objects List of objects to insert
                         * @return SQL insert command as a string
                         */
                        public String generateInsertSQL(List<?> objects) {
                            validateObjects(objects);
                    
                            StringBuilder sql = new StringBuilder("INSERT INTO ").append(TABLE_NAME).append(" (");
                    """);

        List<String> columnNames = new ArrayList<>();
        for (Element field : classElement.getEnclosedElements()) {
            if (field.getKind() == ElementKind.FIELD) {
                Column column = field.getAnnotation(Column.class);
                if (column != null) {
                    columnNames.add(column.name());
                }
            }
        }

        writer.printf("        sql.append(\"%s\");\n", String.join(", ", columnNames));

        writer.append("""
                        sql.append(") VALUES ");
                
                        for (Object object : objects) {
                            sql.append(generateValuesForObject(object)).append(", ");
                        }
                
                        sql.delete(sql.length() - 2, sql.length()).append(";");
                        return sql.toString();
                    }
                
                """);
    }

    private void createValidateObjects(PrintWriter writer) {
        writer.println("""
                        private void validateObjects(List<?> objects) {
                            if (objects == null || objects.isEmpty()) {
                                throw new IllegalArgumentException("List of objects is null or empty.");
                            }
                    
                            for (Object object : objects) {
                                try {
                                    Validator.validate(object);
                                } catch (Exception e) {
                                    throw new IllegalArgumentException("Validation failed: " + e.getMessage());
                                }
                            }
                        }
                    """);
    }

    private void createGenerateValuesForObject(PrintWriter writer, Element classElement) {
        writer.println("""
                        private String generateValuesForObject(Object object) {
                            StringBuilder values = new StringBuilder("(");
                    """);

        for (Element field : classElement.getEnclosedElements()) {
            if (field.getKind() == ElementKind.FIELD) {
                String fieldName = field.getSimpleName().toString();
                writer.printf("""
                                    values.append(extractFieldValue(object, "get%s")).append(", ");
                            """, capitalize(fieldName));
            }
        }

        writer.println("""
                            values.delete(values.length() - 2, values.length()).append(")");
                            return values.toString();
                        }
                    """);
    }

    private void createExtractFieldValue(PrintWriter writer) {
        writer.println("""
                        private String extractFieldValue(Object object, String getterName) {
                            try {
                                Method getter = object.getClass().getMethod(getterName);
                                Object value = getter.invoke(object);
                 
                                return "\\'" + value + "\\'";
                            } catch (Exception e) {
                                throw new RuntimeException("Error accessing getter: " + getterName, e);
                            }
                        }
                    }
                    """);
    }

    private String extractTableName(Element classElement) {
        Table table = classElement.getAnnotation(Table.class);
        return table != null && !table.name().isEmpty() ? table.name() : classElement.getSimpleName().toString().toLowerCase();
    }

    private String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
