# Laboratory work 3

## Anastasiia Usenko IM-23

### Variant 2

The project includes an annotation processor that automatically generates SQL classes for each class annotated with 
@SqlGenerator. This processor also validates objects based on the specified constraints and provides methods to generate 
CREATE TABLE and INSERT SQL queries dynamically.

## Features

1. Field Validation: Custom field validation annotations such as NotNull, StringLength, MaxValue, and MinValue are used 
to enforce constraints on object fields. If an annotation is applied to an incorrect field type (e.g., StringLength on 
an Integer field), a configuration error is raised.

2. SQL Generator Class Creation: The processor generates SQL generator classes for each annotated class, such as 
UserSQLGenerator, OrderSQLGenerator, etc. These classes include methods for generating SQL commands:
   * generateCreateTableSQL() for creating the table schema.
   * generateInsertSQL() for creating SQL insert statements for a list of objects.
## Requirements

You must have [Java 21](https://www.oracle.com/cis/java/technologies/downloads/#java21) 
and [Maven](https://maven.apache.org/download.cgi) installed on your machine to run this application.

## Installation

1. Clone this repository with the command

```bash
git clone https://github.com/nastiausenko/Java_Labs.git
```

## Usage

1. Navigate to the Lab3 folder from the root directory

```bash
cd Lab3
```

2. Build the processor

```bash
mvn clean package
```

3. Navigate to the Lab2 folder from the root directory

```bash
cd Lab2
```

4. Build the application

```bash
mvn clean package
```

> NOTE: Generated classes can be found in the `target/generated-sources/annotations` directory.

5. Run the application

```bash
java -jar target/lab2-0.0.1-SNAPSHOT.jar
```