# Cosmic Escape

**Introduction to Software Engineering (CMPT 276) Fall 2025**

A 2D top-down game built using **LitiEngine**.

**Game Demo:** https://youtu.be/d250ylmZjH8

## Acknowledgement
This repository is a personal copy for portfolio and demonstration purposes.
Original repository owned by the course instructor.

## Project Summary
This repository contains a maze-style arcade game developed as part of a group project at SFU.
The project followed a structured software development lifecycle, comprising four phases that progressed from design and planning to implementation, testing, and final presentation.

The goal of the project was to develop a playable arcade-style game featuring a single main player, at least one stationary enemy, at least one moving enemy, and collectible rewards, as specified by the course requirements.

This README focuses on build and test instructions. Detailed design artifacts, reports, and reflections are organized in the `design` and `documents` folders as outlined below.

## Documentation Overview

### Design Phase (Phase 1)
The `design` folder contains materials from the initial planning and design phase, including:
- Project description and requirements analysis
- Game mockups
- UML diagrams
- Use case diagrams

This phase focuses on conceptual design and system planning prior to implementation.

### Implementation & Reporting Phases (Phases 2–4)
The `documents` folder contains formal reports corresponding to each project phase:

- **Phase 2 – Implementation (Coding Phase)**  
  Covers division of work, implementation details, and challenges encountered during development.

- **Phase 3 – Testing Phase**  
  Documents the testing strategy, test cases, and results used to validate the game.

- **Phase 4 – Final Project Overview**  
  Provides a complete overview of the final product, including gameplay description, final design decisions, and team reflection.

For a comprehensive understanding of the completed project and gameplay, refer to:  
**`documents/Phase4Report`**

## Team Members
* **Gonzales, Michael**
* **Tatla, Jugraj** 
* **Bhasin, Praneet Kaur** 
* **Wang, Zihan** 
* **Dalsaniya, Palash** 

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
