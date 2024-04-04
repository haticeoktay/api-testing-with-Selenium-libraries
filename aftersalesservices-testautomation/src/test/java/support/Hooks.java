package support;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.google.common.collect.ImmutableMap;
import config.Configuration;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.apache.commons.io.output.WriterOutputStream;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import util.CurlParser;
import util.testrail.TestRailsLogger;

import java.io.PrintStream;
import java.io.StringWriter;
import java.util.HashMap;

import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;

public class Hooks {
    private static ExtentReports extentReports;
    private static ExtentTest extentTest;


    @BeforeAll
    public static void setUp() {
        Configuration.loadAllConfigs();
        //RestAssured.port = "";
        //RestAssured.basePath = "";
        RestAssured.baseURI = BasePath.getBasePath();
        setUpAllureEnv();
    }

    public static void setUpAllureEnv() {
        String tags = System.getProperty("cucumber.filter.tags");
        if (tags == null || tags.isEmpty()) {
            tags = "ALL";
        }
        String testRun = System.getProperty("testRunID");
        HashMap<String, String> properties = new HashMap<String, String>();
        properties.put("Environment:", Environment.getEnvironment().toUpperCase());
        if (testRun != null) {
            properties.put("Test Run:", "https://project.testrail.io/index.php?/runs/view/" + testRun);
        }
        properties.put("Base url:", BasePath.getBasePath());
        properties.put("Tags:", tags);
        ImmutableMap<String, String> immutableMap = ImmutableMap.copyOf(properties);
        allureEnvironmentWriter(immutableMap, System.getProperty("user.dir") + "/build/allure-results/");
    }

    StringWriter requestWriter = new StringWriter();
    StringWriter responseWriter = new StringWriter();

    @Before
    public void beforeScenario(Scenario scenario) {
        PrintStream requestCapture = new PrintStream(new WriterOutputStream(requestWriter, "UTF-8"), true);
        PrintStream responseCapture = new PrintStream(new WriterOutputStream(responseWriter, "UTF-8"), true);
        RestAssured.filters(new RequestLoggingFilter(requestCapture), new ResponseLoggingFilter(responseCapture));
    }

    @After(order = 1)
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            String request = requestWriter.toString();
            String curl = CurlParser.getCurls(request);
            scenario.attach("Request: \n" + request, "text/plain", "Requests");
            scenario.attach(curl, "text/plain", "Curls");
            scenario.attach("Response: \n" + responseWriter.toString(), "text/plain", "Responses");
            TestRailsLogger.logResultToTestRail(scenario, curl);
        } else {
            TestRailsLogger.logResultToTestRail(scenario, "");
        }
    }

    @After(order = 0)
    public void afterScenarioFinish(Scenario scenario) {
        if (scenario.isFailed()) {
            System.out.println("FAILED");
        }else {
            System.out.println("PASSED");
        }
        System.out.println("-----------------End of Scenario-----------------");
    }
    @AfterMethod
    public void afterEachTestMethod(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        extentTest = extentReports.createTest(testName);

        if (result.getStatus() == ITestResult.FAILURE) {
            extentTest.fail(result.getThrowable());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            extentTest.pass("Test passed");
        } else {
            extentTest.skip("Test skipped");
        }
    }

    @AfterSuite
    public void tearDown() {
        extentReports.flush();
    }


}
