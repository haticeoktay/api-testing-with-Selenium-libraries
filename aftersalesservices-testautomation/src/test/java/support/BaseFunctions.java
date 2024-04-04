package support;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONArray;
import step.definitions.APITestSteps;


import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.hamcrest.collection.IsMapContaining.hasKey;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

import org.json.JSONObject;


public class BaseFunctions {
    Response response;
    public static String username = "Qe$26BERsdg^_)NDt8d199";
    public static String password = "<M64%H09!@vdsG>XF7488";

    public BaseFunctions(Response response) {
        this.response = response;
    }


    public void theResponseHaveValueEqualToText(String value2, String text, String apiname) {

        switch (apiname) {
            //value:text, integer:integer
            case "vehicle-generations":
                JsonPath jsonPath = APITestSteps.response.jsonPath();
                int valueint = jsonPath.getInt("find { it.text == " + text + "}.value");
                assertEquals(valueint, Integer.parseInt(value2));
                break;
            //value:text,integer:string
            case "targets":
                jsonPath = APITestSteps.response.jsonPath();
                int valuestring = jsonPath.getInt("find { it.text == '" + text + "' }.value");
                assertEquals(valuestring, Integer.parseInt(value2));
                break;
            //value:text,integer:string
            case "entity-types":
                jsonPath = APITestSteps.response.jsonPath();
                valuestring = jsonPath.getInt("find { it.text == '" + text + "' }.value");
                assertEquals(valuestring, Integer.parseInt(value2));
                break;
            //value:text,string:string
            case "ecus":
                jsonPath = APITestSteps.response.jsonPath();
                String valuestringecus = jsonPath.getString("find { it.text == '" + text + "' }.value");
                assertEquals(valuestringecus, value2);
                break;
            default:
                break;
        }
    }

    public void theResponseContainsArrayValueForIntoTheAp(String text) {

        if (text.equals("applicationExtensions")) {
            APITestSteps.response.then().body(text, hasItems(".s19", ".hex", ".mot", ".bin"));
        } else if (text.equals("validationExtensions")) {
            APITestSteps.response.then().body(text, hasItems(".sig", ".crc", ".asc"));
        } else {
            throw new IllegalArgumentException("Invalid input: " + text);
        }
    }

    public void theReturnedStatusCodeIsForPostRequest(String responseMessage) {
        int responseCode = APITestSteps.responsepost.then().extract().statusCode();
        assertEquals(responseMessage, String.valueOf(responseCode));
    }

    public void theReturnedMessageIsForPostRequest(String responseMessage) {
        APITestSteps.responsepost
                .then()
                .body("errors.error_description[0]", equalTo(responseMessage));
    }

    public void theRequestCreatesEcuPartsWithTheParameter(String apiname, String phwAcronym) throws URISyntaxException {
        response = given()
                .relaxedHTTPSValidation()
                .contentType("application/x-www-form-urlencoded")
                .formParam("grant_type", "password")
                .formParam("client_id", "packager-client")
                .formParam("username", APITestSteps.username)
                .formParam("password", APITestSteps.password)
                .formParam("scope", "offline_access")
                .when()
                .post(new URI("https://auth.togg.com.tr/realms/OTAPRODUCTION/protocol/openid-connect/token"));
        String responseBody = response.getBody().asString();
        JsonObject jsonObject = new Gson().fromJson(responseBody, JsonObject.class);
        String accessToken = jsonObject.get("access_token").getAsString();


//        assertTrue(String.format("Endpoint '%s' is not defined in the json file.", apiname), Endpoints.endpointDefined(apiname));
        RequestSpecification req = given();

        JSONObject requestBody = new JSONObject();
        requestBody.put("hwAcronym", phwAcronym);


        APITestSteps.responsepost = req
                .relaxedHTTPSValidation()
                .header("Authorization", "Bearer " + accessToken)
                .contentType("application/json")
                .body(requestBody.toString())
                .when()
                .post(new URI(APITestSteps.BASE_PATH));


    }

    public void theRequestDecoderWithTheParameter(String model, String varcod) throws URISyntaxException {

        System.out.println("base path is: " + APITestSteps.BASE_PATH);
        RequestSpecification req = given();

        JSONObject requestBody = new JSONObject();
        requestBody.put("model", model);
        requestBody.put("varcod", varcod);

        try {
            APITestSteps.responsepost = req
//                    .log().all()
                    .relaxedHTTPSValidation()
                    .contentType("application/json")
                    .body(requestBody.toString())
                    .when()
                    .post(new URI(APITestSteps.BASE_PATH));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        System.out.println("request is: " + requestBody);

    }

    public void theRequestGeneratecmacWithTheEkstraParameter(String vin, String vcname, String vcstring, String ekstraField, String ecusnList, String ecunameList, String ecupnList) throws URISyntaxException {

        List<Map<String, String>> eListData = null;

//        assertTrue(String.format("Endpoint '%s' is not defined in the json file.", "generatecmac"), Endpoints.endpointDefined("generatecmac"));
        System.out.println("base path is: " + APITestSteps.BASE_PATH);
        RequestSpecification req = given();


        JSONObject requestBody = new JSONObject();
        requestBody.put("VIN", vin);
        requestBody.put("VarCodName", vcname);
        requestBody.put("VarCodString", vcstring);
        requestBody.put("ekstraField", ekstraField);

        // Split the comma-separated values
        String[] snArray = ecusnList.split(",");
        String[] nameArray = ecunameList.split(",");
        String[] pnArray = ecupnList.split(",");

        // Construct the EList array
        JSONArray eListArray = new JSONArray();
        for (int i = 0; i < snArray.length; i++) {
            JSONObject eListItem = new JSONObject();
            eListItem.put("ECUSn", snArray[i]);
            eListItem.put("ECUName", nameArray[i]);
            eListItem.put("ECUPn", pnArray[i]);
            eListArray.add(eListItem); // Use add method instead of put
        }
        requestBody.put("ECUList", eListArray);

        try {
            APITestSteps.responsepost = req
                    .relaxedHTTPSValidation()
                    .auth().preemptive().basic(username, password)  // Set basic authentication
                    .contentType("application/json")
                    .body(requestBody.toString())
                    .when()
                    .post(new URI(APITestSteps.BASE_PATH));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        System.out.println("request is: " + requestBody);
    }


    public void theRequestCreatesGeneratecmacWithTheParametervınasnumber(String vin, String vcname, String vcstring, String ecusnList, String ecunameList, String ecupnList) throws URISyntaxException {

        List<Map<String, String>> eListData = null;
//        assertTrue(String.format("Endpoint '%s' is not defined in the json file.", "generatecmac"), Endpoints.endpointDefined("generatecmac"));
        System.out.println("base path is: " + APITestSteps.BASE_PATH);
        RequestSpecification req = given();

        JSONObject requestBody = new JSONObject();
        Long vnAsNumber = Long.parseLong(vin);
        requestBody.put("VIN", vnAsNumber);
        requestBody.put("VarCodName", vcname);
        requestBody.put("VarCodString", vcstring);

        // Split the comma-separated values
        String[] snArray = ecusnList.split(",");
        String[] nameArray = ecunameList.split(",");
        String[] pnArray = ecupnList.split(",");

        // Construct the EList array
        JSONArray eListArray = new JSONArray();
        for (int i = 0; i < snArray.length; i++) {
            JSONObject eListItem = new JSONObject();
            eListItem.put("ECUSn", snArray[i]);
            eListItem.put("ECUName", nameArray[i]);
            eListItem.put("ECUPn", pnArray[i]);
            eListArray.add(eListItem); // Use add method instead of put
        }
        requestBody.put("ECUList", eListArray);

        try {
            APITestSteps.responsepost = req
                    .relaxedHTTPSValidation()
                    .auth().preemptive().basic(username, password)  // Set basic authentication
                    .contentType("application/json")
                    .body(requestBody.toString())
                    .when()
                    .post(new URI(APITestSteps.BASE_PATH));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        System.out.println("request is: " + requestBody);
    }

    public void theRequestCreatesGeneratecmacWithTheParameterEUCSNasnumber(String vin, String vcname, String vcstring, String ecusnList, String ecunameList, String ecupnList) throws URISyntaxException {

        List<Map<String, String>> eListData = null;
//        assertTrue(String.format("Endpoint '%s' is not defined in the json file.", "generatecmac"), Endpoints.endpointDefined("generatecmac"));
        System.out.println("base path is: " + APITestSteps.BASE_PATH);
        RequestSpecification req = given();

        JSONObject requestBody = new JSONObject();
        requestBody.put("VIN", vin);
        requestBody.put("VarCodName", vcname);
        requestBody.put("VarCodString", vcstring);

        // Split the comma-separated values
        String[] snArray = ecusnList.split(",");
        String[] nameArray = ecunameList.split(",");
        String[] pnArray = ecupnList.split(",");

        // Construct the EList array
        JSONArray eListArray = new JSONArray();
        for (int i = 0; i < snArray.length; i++) {
            JSONObject eListItem = new JSONObject();
            eListItem.put("ECUSn", Long.parseLong(snArray[i]));
            eListItem.put("ECUName", nameArray[i]);
            eListItem.put("ECUPn", pnArray[i]);
            eListArray.add(eListItem); // Use add method instead of put
        }
        requestBody.put("ECUList", eListArray);

        try {
            APITestSteps.responsepost = req
                    .relaxedHTTPSValidation()
                    .auth().preemptive().basic(username, password)  // Set basic authentication
                    .contentType("application/json")
                    .body(requestBody.toString())
                    .when()
                    .post(new URI(APITestSteps.BASE_PATH));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        System.out.println("request is: " + requestBody);
    }

    public void duplicateItemsInEcuList(String vin, String vcname, String vcstring, String ecusnList, String ecunameList, String ecupnList, String ecusnList2, String ecunameList2, String ecupnList2) throws URISyntaxException {

        List<Map<String, String>> eListData = null;
//        assertTrue(String.format("Endpoint '%s' is not defined in the json file.", "generatecmac"), Endpoints.endpointDefined("generatecmac"));
        System.out.println("base path is: " + APITestSteps.BASE_PATH);
        RequestSpecification req = given();

        JSONObject requestBody = new JSONObject();
        requestBody.put("VIN", vin);
        requestBody.put("VarCodName", vcname);
        requestBody.put("VarCodString", vcstring);

        // Construct the EList array
        JSONArray ecuListArray = new JSONArray();

        // First EList item
        JSONObject eListItem1 = new JSONObject();
        eListItem1.put("ECUSn", ecusnList);
        eListItem1.put("ECUName", ecunameList);
        eListItem1.put("ECUPn", ecupnList);
        ecuListArray.add(eListItem1);

        // Second EList item
        JSONObject eListItem2 = new JSONObject();
        eListItem2.put("ECUSn", ecusnList2);
        eListItem2.put("ECUName", ecunameList2);
        eListItem2.put("ECUPn", ecupnList2);
        ecuListArray.add(eListItem2);

        requestBody.put("ECUList", ecuListArray);
        try {
            APITestSteps.responsepost = req
                    .relaxedHTTPSValidation()
                    .auth().preemptive().basic(username, password)  // Set basic authentication
                    .contentType("application/json")
                    .body(requestBody.toString())
                    .when()
                    .post(new URI(APITestSteps.BASE_PATH));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        System.out.println("request is: " + requestBody);
    }


    public void theResponseEqualToFromTheGenaratecmacRequest(int index, String cmacText, String cmac) {
        String jsonPathExpression = String.format("data.parameters[%d].%s", index, cmacText);

        APITestSteps.responsepost
                .then()
                .body(jsonPathExpression, equalTo(cmac));


        //to print the entire response body of a POST request
        APITestSteps.responsepost
                .then()
                .statusCode(200)
                .extract()
                .response();

        System.out.println("response is: " + APITestSteps.responsepost.asString());

    }

    public static void theEmptyRequest() throws URISyntaxException {

        System.out.println("base path is: " + APITestSteps.BASE_PATH);
        RequestSpecification req = given();

        JSONObject requestBody = new JSONObject();

        try {
            APITestSteps.responsepost = req
                    .relaxedHTTPSValidation()
                    .contentType("application/json")
                    .body(requestBody.toString())
                    .when()
                    .post(new URI(APITestSteps.BASE_PATH));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void theResponseEqualToFromTheRequestIntoTheArray(String number, String value, String type, String valueofvalue, String size) {
        int index = Integer.parseInt(number) - 1;
        switch (type) {
            //value:text, integer:integer
            case "integer":
                APITestSteps.responsepost
                        .then()
                        .statusCode(200)
                        .assertThat()
                        .body("size()", is(Integer.parseInt(size)))
                        .body("[" + index + "]." + value + "", equalTo(Integer.parseInt(valueofvalue)));
                break;
            case "string":
                APITestSteps.responsepost
                        .then()
                        .statusCode(200)
                        .assertThat()
                        .body("size()", is(Integer.parseInt(size)))
                        .body("[" + index + "]." + value + "", equalTo(valueofvalue));
                break;
        }

    }


    public void testParallelRequests(String numberOfRequests) throws URISyntaxException, NoSuchAlgorithmException, KeyManagementException {

//            int numberOfRequests = 50;
        Thread thread = new Thread();
        String[] deviceNames = new String[Integer.parseInt(numberOfRequests)];
        for (int i = 0; i < Integer.parseInt(numberOfRequests); i++) {
            deviceNames[i] = "BVT" + (i + 1) + "CX";
        }
        System.out.println("device name 1: " + deviceNames[0]);

        // Set up the base request
        RequestSpecification request = given()
                .baseUri("https://auth.togg.com.tr:8883")
                .header("Content-Type", "application/json");

        // Run requests in parallel with different device names
        Response[] responses = new Response[Integer.parseInt(numberOfRequests)];
        for (int i = 0; i < Integer.parseInt(numberOfRequests); i++) {
            String requestBody = "{\"deviceName\": \"" + deviceNames[i] + "\"}";
            request.body(requestBody);
            final int j = i;
            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    responses[j] = request.post("/create-device");
                }
            });
            thread.start();
        }

        // Wait for all requests to complete
        for (int i = 0; i < Integer.parseInt(numberOfRequests); i++) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Assert responses as needed
        for (int i = 0; i < Integer.parseInt(numberOfRequests); i++) {
            assert responses[i].getStatusCode() == 200;
            // Add additional assertions as needed
        }
    }

    public void theResponseEqualToFromThePartNumberRequest(String partNumbertext, String partNumber) {
        APITestSteps.responsepost
                .then()
                .body(partNumbertext, equalTo(partNumber));

        //to print the entire response body of a POST request
        APITestSteps.responsepost
                .then()
                .statusCode(200)
                .extract()
                .response();

        System.out.println(APITestSteps.responsepost.asString());

    }
}
