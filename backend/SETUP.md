# Setup guide

## Target: SCC Spring app

## Prerequisites
* Install latest JDK (at least JDK 8 should be installed on your machine) - Get Java 8 from [here](https://www.java.com/en/download/)
* Install Maven - Get Maven from [here](https://maven.apache.org/download.cgi)
* Make sure to have a reliable internet connection (only required if running the app with Firebase database connection)

## Project setup

### Running the project from SSH
If on a Mac or Linux machine, you will be able to build and run the project using SSH. If on a Windows machine, you will need to use the CMD.

### If on Mac or Linux:
1. Open a SSH window
2. Navigate to the project's location (should be `.../SmartCarCleaner/`)
3. Execute the SSH command `mvn -f backend/pom.xml clean package`
4. Execute the SSH command `java -jar backend/target/app-{currentVersion}.jar`
5. The SCC Spring app should now start-up and output should be displayed in the SSH window
5.1 To stop the app, click CTRL + C on your keyboard when in the SSH window

### If on Windows:
1. Open a command-line (CMD) window
2. Navigate to the project's location (should be `...\SmartCarCleaner\`)
3. Execute the CMD command `mvn -f backend/pom.xml clean package`
4. Execute the CMD command `java -jar backend/target/app-{currentVersion}.jar`
5. The SCC Spring app should now start-up and output should be displayed in the CMD window
5.1 To stop the app, click the CTRL + C on your keyboard when in the CMD window

### Running the project from IntelliJ IDEA
1. Open the project located in the folder `.../SmartCarCleaner/backend`
2. Build the project using the build menu (`Build>Build Project`)
3. Select the SmartCarCleanerApplication configuration from the Application Configuration dropdown
4. Run the project by clicking the green Run button
5. The SCC Spring app should now start-up and output should be displayed in the Run window of the IDE
5.1 To stop the app, click the red Stop process button in the IDE

## Running the JUnit tests
To run the JUnit tests locally, open a SSH/CMD window similarly to the `Running the project from SSH` section.
The tests are run using the command `mvn -f backend/pom.xml test`.
