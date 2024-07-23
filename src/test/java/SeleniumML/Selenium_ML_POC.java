package SeleniumML;
import weka.classifiers.Classifier;
import weka.classifiers.functions.Logistic;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Utils;
import weka.core.converters.ConverterUtils.DataSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
public class Selenium_ML_POC {
    public static void main(String[] args) throws Exception {

        String relativePath = "src/test/java/SeleniumML/training_data.arff";
        String filePath = System.getProperty("user.dir") + "/" + relativePath;
        // Load historical test data
        DataSource source = new DataSource(filePath);
        Instances data = source.getDataSet();
        data.setClassIndex(data.numAttributes() - 1); // Set the class index to the 'priority' attribute

        // Train machine learning model
        Classifier model = new Logistic();
        model.buildClassifier(data);

        // Predict priority for a new test case
        double executionTime = 9.0;
        int numberOfSteps = 4;
        String isCritical = "yes";
        String testCaseType = "functional";
        String lastExecutionResult = "";
        String module = "login";

        Instance newTestCase = createNewTestCaseInstance(data, executionTime, numberOfSteps, isCritical, testCaseType, lastExecutionResult, module);
        double[] distribution = model.distributionForInstance(newTestCase);
        double priority = distribution[1]; // Assuming the second class value is the positive class

        // Print the priority score
        System.out.println("Priority score for the new test case: " + priority);

        // If priority is above a certain threshold, execute the test case
        if (priority > 0.5) {  // Adjust threshold as needed
            runSeleniumTest();
        } else {
            System.out.println("Test case not prioritized.");
        }
    }

    private static Instance createNewTestCaseInstance(Instances data, double executionTime, int numberOfSteps, String isCritical, String testCaseType, String lastExecutionResult, String module) {
        // Create a new instance with the same structure as the dataset
        double[] vals = new double[data.numAttributes()];
        vals[0] = executionTime; // executionTime
        vals[1] = numberOfSteps; // numberOfSteps
        vals[2] = data.attribute(2).indexOfValue(isCritical); // isCritical
        vals[3] = data.attribute(3).indexOfValue(testCaseType); // testCaseType
        vals[4] = data.attribute(4).indexOfValue(lastExecutionResult); // lastExecutionResult
        vals[5] = data.attribute(5).indexOfValue(module); // module

        // Set the class value to missing (NaN)
        vals[data.classIndex()] = Utils.missingValue();

        // Create a dense instance
        Instance newInstance = new DenseInstance(1.0, vals);
        // Assign the dataset to the new instance
        newInstance.setDataset(data);

        return newInstance;
    }

    private static void runSeleniumTest() {


        // Initialize ChromeDriver
        WebDriver driver = new ChromeDriver();

        try {
            // Open Amazon homepage
            driver.get("https://www.amazon.com");

            // Example test case: search for a product
            WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
            searchBox.sendKeys("laptop");
            searchBox.submit();

            // Wait for results to load and check if results are displayed
            Thread.sleep(3000); // This is a simple wait; better to use WebDriverWait in real cases

            WebElement results = driver.findElement(By.cssSelector("span.a-color-state.a-text-bold"));
            if (results.isDisplayed()) {
                System.out.println("Test case passed: Search results are displayed.");
            } else {
                System.out.println("Test case failed: Search results are not displayed.");
            }
        } catch (Exception e) {
            System.out.println("Test case failed with exception: " + e.getMessage());
        } finally {
            // Close the browser
            driver.quit();
        }
    }
}
