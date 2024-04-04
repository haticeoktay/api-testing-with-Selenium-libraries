package util.testrail;

import io.cucumber.core.backend.TestCaseState;
import io.cucumber.java.Scenario;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TestRailsLogger {
    static String baseUrl = "";
    static String usernameTestRail = null;
    static String passwordTestRail = null;
    static APIClient client = null;
    private static final int FAIL_STATE = 5;
    private static final int SUCCESS_STATE = 1;
    private static final String SUCCESS_COMMENT = "This test passed with cucumber automation.";
    private static final String FAILED_COMMENT = "This test failed with cucumber automation.";

    //TODO: something to load this from console
    static String testRunID = null;
    static String jenkinsLink = null;

    static APIClient testRailApiClient() {
        if (client == null) {
            client = new APIClient(baseUrl);
            client.setUser(usernameTestRail);
            client.setPassword(passwordTestRail);
        }
        return client;
    }

    public static void logResultToTestRail(Scenario scenario, String curl) {
        if (testRunID != null) {
            String caseId = getCaseId(scenario);
            if (!Objects.equals(caseId, "")) {
                Map<String, Serializable> data = new HashMap<>();
                String comment = "\n";
                comment += "Scenario Name: " + scenario.getName() + "\n";
                if (!scenario.isFailed()) {
                    data.put("status_id", SUCCESS_STATE);
                    comment += SUCCESS_COMMENT + "\n";
                } else {
                    data.put("status_id", FAIL_STATE);
                    comment += FAILED_COMMENT + "\n";
                    if ((jenkinsLink != null) && (!jenkinsLink.isEmpty())) {
                        comment += "Jenkins Link: " + jenkinsLink + "\n";
                    }
                    comment += getError(scenario);
                    comment += "\n";
                    comment += "Last curl: \n";
                    comment += curl;
                }
                data.put("comment", comment);
                try {
                    testRailApiClient().sendPost("add_result_for_case/" + testRunID + "/" + caseId, data);
                } catch (Exception e) {
                    System.out.println("Error pushing result to Test Rail. Please check if Test Rail is working as expected.");
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private static final Field field = FieldUtils.getField(Scenario.class, "delegate", true);
    private static Method getError;

    static Throwable getError(Scenario scenario) {
        try {
            final TestCaseState testCase = (TestCaseState) field.get(scenario);
            if (getError == null) {
                getError = MethodUtils.getMatchingMethod(testCase.getClass(), "getError");
                getError.setAccessible(true);
            }
            return (Throwable) getError.invoke(testCase);
        } catch (Exception e) {
            System.out.println("Error receiving exception");
        }
        return null;
    }

    private static String getCaseId(Scenario scenario) {
        String caseId = "";
        for (String s : scenario.getSourceTagNames()) {
            if (s.contains("TestRail")) {
                String[] res = s.split("(\\(.*?)");
                caseId = res[1].substring(0, res[1].length() - 1);
            }
        }
        return caseId;
    }

}