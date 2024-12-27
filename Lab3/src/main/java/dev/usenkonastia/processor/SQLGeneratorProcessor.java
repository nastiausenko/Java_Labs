package dev.usenkonastia.processor;

import com.google.auto.service.AutoService;
import dev.usenkonastia.processor.validation.annotations.MaxValue;
import dev.usenkonastia.processor.validation.annotations.MinValue;
import dev.usenkonastia.processor.validation.annotations.NotNull;
import dev.usenkonastia.processor.validation.annotations.StringLength;

import javax.annotation.processing.*;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@AutoService(Processor.class)
@SupportedAnnotationTypes("dev.usenkonastia.processor.SqlGenerator")
@SupportedSourceVersion(javax.lang.model.SourceVersion.RELEASE_17)
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

            writer.printf("package %s;%n%n", packageName);
            writer.printf("public class %s {%n%n", generatorClassName);

            // Generate CREATE TABLE SQL
            writer.println("    public String generateCreateTableSQL() {");
            writer.println("        StringBuilder sql = new StringBuilder();");
            writer.printf("        sql.append(\"CREATE TABLE %s (\\n\");%n", extractTableName(classElement));

            writer.println("        sql.append(\"    id SERIAL PRIMARY KEY,\\n\"); // Add id field");

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
                        StringLength stringLength =
                                field.getAnnotation(StringLength.class);
                        if (stringLength != null) {
                            columnDefinition.append(" CHECK(LENGTH(").append(column.name()).append(") >= ")
                                    .append(stringLength.min()).append(" AND LENGTH(").append(column.name())
                                    .append(") <= ").append(stringLength.max()).append(")");
                        }

                        // Handle MinValue and MaxValue annotations
                       MinValue minValue =
                                field.getAnnotation(MinValue.class);
                       MaxValue maxValue =
                                field.getAnnotation(MaxValue.class);
                        if (minValue != null) {
                            columnDefinition.append(" CHECK(").append(column.name()).append(" >= ")
                                    .append(minValue.value()).append(")");
                        }
                        if (maxValue != null) {
                            columnDefinition.append(" CHECK(").append(column.name()).append(" <= ")
                                    .append(maxValue.value()).append(")");
                        }

                        writer.printf("        sql.append(\"    %s,\\n\");%n", columnDefinition.toString());
                    }
                }
            }

            writer.println("        sql.delete(sql.length() - 3, sql.length()); // Remove last comma");
            writer.println("        sql.append(\")\\n);\");");
            writer.println("        return sql.toString();");
            writer.println("    }");

            writer.println();

            // Generate INSERT SQL
            // Generate INSERT SQL
            writer.println("    public String generateInsertSQL(Object object) {");
            writer.println("        StringBuilder sql = new StringBuilder();");
            writer.printf("        sql.append(\"INSERT INTO %s (\");%n", extractTableName(classElement));

// Динамічні списки для полів і значень
            writer.println("        StringBuilder columnNames = new StringBuilder();");
            writer.println("        StringBuilder placeholders = new StringBuilder();");

            for (Element field : classElement.getEnclosedElements()) {
                if (field.getKind() == ElementKind.FIELD) {
                    Column column = field.getAnnotation(Column.class);
                    if (column != null) {
                        String columnName = column.name();
                        writer.printf("        if (columnNames.length() > 0) columnNames.append(\", \");%n");
                        writer.printf("        if (placeholders.length() > 0) placeholders.append(\", \");%n");
                        writer.printf("        columnNames.append(\"%s\");%n", columnName);
                        writer.println("        placeholders.append(\"?\");");
                    }
                }
            }

// Завершення SQL-запиту
            writer.println("        sql.append(columnNames).append(\") VALUES (\").append(placeholders).append(\");\");");
            writer.println("        return sql.toString();");
            writer.println("    }");


            writer.println("}");

        } catch (IOException e) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Error generating SQLGenerator class for " + className);
        }
    }

    private String extractTableName(Element classElement) {
        Table table = classElement.getAnnotation(Table.class);
        return (table != null && !table.name().isEmpty()) ? table.name() : classElement.getSimpleName().toString().toLowerCase();
    }
}
