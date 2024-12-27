# Laboratory work 1

## Anastasiia Usenko IM-23

### Variant 3

This project is designed to generate, process, and analyze data about chthonic creatures. 
The program generates random creatures, processes them using custom collectors and aggregation 
mechanisms to analyze their attack power. The program also filters creatures by type and determines 
outliers based on statistical methods (interquartile range).

## Technologies
- **Java 23**: The programming language in which the entire project is realized.
- **Stream API**: For processing collections and performing operations on data.
- **Custom Collectors and Gatherers**: Used to aggregate and filter data.
- **Java Records**: To create simple data containers, such as `ChthonicCreature`.

## Requirements

You must have [Java 23](https://www.oracle.com/ua/java/technologies/downloads/#jdk23-linux) 
and [Maven](https://maven.apache.org/download.cgi) installed on your machine to run this application.

## Installation

1. Clone this repository with the command

```bash
git clone https://github.com/nastiausenko/Java_Labs.git
```

## Usage

1. Navigate to the Lab1 folder

```bash
cd Lab1
```

2. Build the application

```bash
mvn clean install
```

3. Run the application

```bash
java --enable-preview -cp target/classes dev.usenkonastia.Main
```