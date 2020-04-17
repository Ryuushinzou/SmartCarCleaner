#!/bin/bash
mvn -f backend/pom.xml clean package
java -jar backend/target/app-0.0.1-SNAPSHOT.jar
