# Language Learning Application (Eng-Viet)
## Features
* Application GUI designed using JavaFX
* Search autocomplete
* Included Word Modification, Word Addition and Removal
* Reads data from SQL database
* Translation API using AWS Translate
* Word Pronunciations using AWS Polly

# Installation
## Requirements
* Java Development Kit (JDK): Download the JDK from https://www.oracle.com/java/technologies/downloads/ or from an IDE such as IntelliJ. 

* Setup the JAVA_HOME variable:

Go to This PC -> Advanced System Settings, then click on the Environment Variable button. Below the "User variables for ..." press "New..." and add JAVA_HOME with the path to your jdk.

Add the Value of the JAVA_HOME variable into the Path Variable as well by clicking the "New" button, and insert the path (Add \bin at the end of the JAVA_HOME path as well).
![alt text](image.png)

Do the same for the System variables below as well.

* Setup database: I will use MySQL for demonstration.

Setup MySQL using the Installer, then open up the MySQL CLI.

Login using the credentials you set during the installation then run the following commands:
```bash
CREATE DATABASE dictionary;
USE dictionary;
SOURCE PATH_TO_FILE/dictionary.sql # The .sql file is included in ~/App/src/main/resources 
```
## Usage
Build the application inside your preferred IDE and run, or do the following:

Go to the Dictionary Application's project folder and run Maven:
 ```bash
cd $Project_Name/App
 ./mvnw javafx:run 
```

