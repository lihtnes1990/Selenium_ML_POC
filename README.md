# Selenium_ML_POC


This repository contains a proof-of-concept (POC) project that integrates Selenium for web automation and WEKA for machine learning. The goal is to prioritize and execute test cases based on their predicted importance using a logistic regression model.
Project Structure

    src/test/java/SeleniumML: Contains the Java classes and ARFF data files for the project.

Requirements

    Java Development Kit (JDK) 8 or higher
    Apache Maven
    WEKA library
    Selenium WebDriver
    Google Chrome and ChromeDriver

Setup
Clone the Repository

bash

git clone https://github.com/lihtnes1990/Selenium_ML_POC.git
cd Selenium-ML-POC

Install Dependencies

Make sure you have Maven installed and set up in your environment. Then, run the following command to install the project dependencies:

bash

mvn clean install

ARFF Data File

Ensure the ARFF data file training_data.arff is located in the src/test/java/SeleniumML directory. This file should contain your historical test data.
Running the Application

Run the main class Selenium_ML_POC:

bash

mvn exec:java -Dexec.mainClass="SeleniumML.Selenium_ML_POC"

Code Overview
Main Class: Selenium_ML_POC

    Loading Historical Data: The application loads historical test data from an ARFF file.
    Training the Model: A logistic regression model is trained using the historical data.
    Predicting Priority: The application predicts the priority of a new test case based on its attributes.
    Executing Test Cases: If the priority is above a certain threshold, the test case is executed using Selenium.

Helper Methods

    createNewTestCaseInstance: Creates a new test case instance with the same structure as the training data.
    runSeleniumTest: Executes a sample Selenium test case (searching for a product on Amazon).

Customization
Adjusting the Threshold

You can adjust the threshold for executing test cases based on the predicted priority:

java

if (priority > 0.5) {  // Adjust threshold as needed
runSeleniumTest();
}

Modifying Test Case Attributes

Update the attributes of the new test case as needed:

java

double executionTime = 9.0;
int numberOfSteps = 4;
String isCritical = "yes";
String testCaseType = "functional";
String lastExecutionResult = "";
String module = "login";

Contributing

Feel free to fork this repository and submit pull requests. For major changes, please open an issue first to discuss what you would like to change.
License

This project is licensed under the MIT License.
