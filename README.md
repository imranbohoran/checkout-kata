# A checkout kata

## Prerequisites
- Java 8
- Maven

## How to build from source
This is a maven based project, so to build (compile, test and build the executable jar)
execute: 

`mvn clean install`

## How to run the application
The executable jar file will be created when the build from source is performed. And then to run;
`java -jar target/supermarket-1.0-SNAPSHOT.jar`

The pricing rules is expected to be in a csv file. The fully qualified path of the file 
containing the rules needs be provided in order to run the application.

