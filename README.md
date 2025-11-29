# Cosmic Escape

**Team 14 - CMPT 276 Fall 2025**

A 2D top-down game built using **LitiEngine**.

**Game Demo:** https://youtu.be/d250ylmZjH8

## Team Members
* **Gonzales, Michael** - mga150@sfu.ca
* **Tatla, Jugraj** - jst21@sfu.ca
* **Bhasin, Praneet Kaur** - pkb19@sfu.ca
* **Wang, Zihan** - zwa171@sfu.ca
* **Dalsaniya, Palash** - pjd5@sfu.ca

## Prerequisites
To build and run this project, ensure you have the following installed:
* **Java Development Kit (JDK):** Version 25 (as specified in `pom.xml`) or a compatible version.
* **Maven:** Apache Maven build tool.

## Building the Project
Navigate to the project root directory (where the `pom.xml` file is located) in your terminal or command prompt and run:

```
mvn clean
mvn compile
mvn package
```
This command cleans previous builds and compiles the code.

## Running the Game
Option 1: Using Maven (Command Line)

Ensure you are in the project root directory.

```
java -jar target/cosmic-escape-1.0.0.jar
```
Note: The game can also be opened manually with the jar file found in `target/cosmic-escape-1.0.0.jar`

Option 2: Using an IDE (VSCode/IntelliJ/Others)
1. Open the project folder in your IDE.
2. Wait for the Maven project to sync/import dependencies.
3. Navigate to `src/main/java/ca/sfu/cmpt276/fall2025/team14/app/CosmicEscape.java`.
4. Right-click the file or find the Run button (often a play icon) next to the main method and select Run.

## Testing
**Automated Tests (Unit & Integration)**

This project uses JUnit 5 for automated testing. To run all unit and integration tests and generate a Jacoco coverage report, go to the prod directory and run the following in your terminal:
```
mvn clean test
mvn jacoco:report
```

The coverage report can be found in `target/site/jacoco/index.html` after the tests complete. 


**Manual Testing / Gameplay Verification**

To manually test the game mechanics and features:
1. Run the game using one of the methods above.
2. Tutorial Level: Upon starting, the game defaults to the Tutorial Level.
    * This level is designed for learning the game power-ups and hazards.
    * It can also be used for testing purposes as it provides a controlled environment to verify mechanics without the immediate difficulty of later levels

## Documentation (Javadocs)
To generate the HTML documentation from the Java source code comments:
1. Open your terminal in the project root directory.
2. Run the following command:
```
mvn javadoc:javadoc
```
3. Once the process completes, the documentation can be found at: `target/reports/apidocs/index.html`
4. Open the index.html file in any web browser to view the Javadocs.

## Artifacts
Artifacts can be found in the Artifacts folder or can be generated as mentioned above
* **Jar file:** `Artifacts/cosmic-escape-1.0.0.jar`
* **Javadocs:** `Artifacts/reports/apidocs/index.html`
* **Jacoco Report:** `Artifacts/site/jacoco/index.html`