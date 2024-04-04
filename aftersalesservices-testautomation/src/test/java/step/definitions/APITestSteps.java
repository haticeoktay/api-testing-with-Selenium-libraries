package step.definitions;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.internal.RestAssuredResponseOptionsGroovyImpl;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import support.Endpoints;
import support.Users;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import io.restassured.module.jsv.JsonSchemaValidator;

import support.BaseFunctions;


public class APITestSteps {

    public static final String BASE_PATH = "http://localhost:52477/api/v1/decoder/";
    public static String BASE_PATH_2 = Endpoints.getURI("ecus");

    public static Response response;
    public static Response responsepost;

    public static String username = "beta-admin";
    public static String password = "tYui3<*oqEDnJ/51R-xB";
    BaseFunctions baseFunctions = new BaseFunctions(response);

    @Given("the API base URI is {string}")
    public void setBaseURI(String baseURI) {
        RestAssured.baseURI = baseURI;
    }

    @Given("the API base path is {string}")
    public void setBasePath(String basePath) {
        RestAssured.basePath = basePath;
    }

    @When("I send a GET request to {string}")
    public void sendGETRequest(String endpoint) {
        response = RestAssured.given()
                .when()
                .get(endpoint)
                .andReturn();
    }

    @Then("the response status code should be {int}")
    public void verifyStatusCode(int expectedStatusCode) {
        assertEquals(expectedStatusCode, response.getStatusCode());
    }


    @Given("the request {string} part-number creates ecu parts with the parameter {string}")
    public void theRequestPartNumberCreatesEcuPartsWithTheParameter(String apiname,String phwAcronym) throws URISyntaxException {
        baseFunctions.theRequestCreatesEcuPartsWithTheParameter(apiname,phwAcronym);
    }
    @Then("the returned status code is for post request: {string}")
    public void theReturnedStatusCodeIsForPostRequest(String responseMessage) {
        baseFunctions.theReturnedStatusCodeIsForPostRequest(responseMessage);
    }
    @Then("the response {string} number {string} equal to {string} {string} from the request into the size of {string} array")
    public void theResponseEqualToFromTheRequestIntoTheArray(String number,String value,String type, String valueofvalue,String size) {
        baseFunctions.theResponseEqualToFromTheRequestIntoTheArray(number,value,type,valueofvalue,size);
    }
    @Then("the returned message is for post request: {string}")
    public void theReturnedMessageIsForPostRequest(String responseMessage) {
        baseFunctions.theReturnedMessageIsForPostRequest(responseMessage);
    }

    @Then("the returned status code is: {string}")
    public void responseCodeIsValidated(String responseMessage) throws InterruptedException {
        int responseCode = response.then().extract().statusCode();
        Assert.assertEquals(responseMessage, String.valueOf(responseCode));
    }
    @And("the schema for the {string} endpoint with {string} response code is correct")
    public void theSchemaIsCorrect(String endpoint, String responseCode) {
        response = response.then().extract().response();
        response.then().assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/" + endpoint + "-" + responseCode + ".json"));
    }

    @Then("the response {int} number {string} equal to {string} from the request for a single value")
    public void theResponseEqualToFromTheRequestForASingleValue(int index,String cmacText, String cmac) {
        baseFunctions.theResponseEqualToFromTheGenaratecmacRequest(index,cmacText,cmac);
    }

    @Given("the request generatecmac creates cmac with the parameters {string} ,{string},{string},{string},{string},{string} vın as number")
    public void theRequestGeneratecmacCreatesCmacWithTheParametersVınAsNumber(String vin, String vcname, String vcstring, String ecusnList, String ecunameList, String ecupnList) throws URISyntaxException {
        baseFunctions.theRequestCreatesGeneratecmacWithTheParametervınasnumber(vin,vcname,vcstring,ecusnList,ecunameList,ecupnList);
    }

    @Given("duplicate items in ECUList with the parameters {string} ,{string},{string},{string},{string},{string},{string},{string},{string}")
    public void duplicateItemsInECUListWithTheParametersVınAsNumber(String vin, String vcname, String vcstring, String ecusnList, String ecunameList, String ecupnList, String ecusnList2, String ecunameList2, String ecupnList2) throws URISyntaxException {
        baseFunctions.duplicateItemsInEcuList(vin,vcname,vcstring,ecusnList,ecunameList,ecupnList,ecusnList2,ecunameList2,ecupnList2);
    }

    @Given("the request is empty")
    public void theRequestIsEmpty() throws URISyntaxException {
        baseFunctions.theEmptyRequest();
    }

    @Given("the request generatecmac creates cmac with the parameters {string} ,{string},{string},{string},{string},{string} ecu as number")
    public void theRequestGeneratecmacCreatesCmacWithTheParametersEcuAsNumber(String vin, String vcname, String vcstring, String ecusnList, String ecunameList, String ecupnList) throws URISyntaxException {
        baseFunctions.theRequestCreatesGeneratecmacWithTheParameterEUCSNasnumber(vin,vcname,vcstring,ecusnList,ecunameList,ecupnList);
    }

    @Given("the request generatecmac with the ekstra parameter {string} ,{string},{string},{string},{string},{string},{string}")
    public void theRequestGeneratecmacWithTheEkstraParameter(String vin, String vcname, String vcstring,String ekstraField, String ecusnList, String ecunameList, String ecupnList) throws URISyntaxException {
        baseFunctions.theRequestGeneratecmacWithTheEkstraParameter(vin,vcname,vcstring,ekstraField,ecusnList,ecunameList,ecupnList);
    }

    @Given("the request decoder run with the parameters {string} ,{string}")
    public void theRequestDecoderRunWithTheParameters(String model, String varcod) throws URISyntaxException {
        baseFunctions.theRequestDecoderWithTheParameter(model,varcod);
    }
}
