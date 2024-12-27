# Laboratory work 2

## Anastasiia Usenko IM-23

### Variant 2

The project implements an object field validator using custom annotations in Java. Custom annotations for validation are 
provided, such as MaxValue, MinValue, NotNull, and StringLength. The project includes two approaches: one that uses 
reflection to dynamically validate object fields using annotations, and another that uses no reflection, using direct 
access to fields to validate values. It displays a performance comparison between the two approaches, analyzing the 
execution time of each validation method.

## Requirements

You must have [Java 23](https://www.oracle.com/ua/java/technologies/downloads/#jdk23-linux) 
and [Maven](https://maven.apache.org/download.cgi) installed on your machine to run this application.

## Installation

1. Clone this repository with the command

```bash
git clone https://github.com/nastiausenko/Java_Labs.git
```

## Usage

1. Navigate to the Lab2 folder from the root directory

```bash
cd Lab2
```

2. Build the application

```bash
mvn clean install
```

3. Run the application

```bash
java --enable-preview -cp target/classes dev.usenkonastia.Main
```