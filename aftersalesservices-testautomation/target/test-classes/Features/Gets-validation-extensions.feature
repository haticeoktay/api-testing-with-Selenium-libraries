@Gets
Feature: Gets validation/extensions
  I want to be sure
  That the Gets endpoint is working as expected


  @tmsLink=01 @severity=critical
    @smoke @regression @prod
  Scenario: Verify status code returned is expected
    Given I do a get to the "validation-extensions" endpoint
    Then the returned status code is: "200"
    And the schema for the "validation-extensions" endpoint with "200" response code is correct


  @tmsLink=02 @severity=critical
  @smoke @regression @prod
  Scenario: Test for checking the response body
    Given I do a get to the "validation-extensions" endpoint
    And Check size of response body for the api validation-extensions
    And the response contains array value for "applicationExtensions" into the api validation-extensions
    And the response contains array value for "validationExtensions" into the api validation-extensions


  @tmsLink=03 @severity=normal
    @smoke @regression
    @2 @3 @prod
  Scenario Outline: Verify amount of returned items is expected
    Given I do a get to the "<Endpoint>" endpoint
    Then the returned status code is: "200"
    Examples:
      | Endpoint              |
      | validation-extensions |

  @tmsLink=04 @severity=normal
    @smoke @regression
    @2 @3 @prod
  Scenario Outline: test for return status code  less than a given time(milliseconds)
    Given I do a get to the "<Endpoint>" endpoint
    When the returned status code is: "<responseCode>" in time "<time>"
    Examples:
      | Endpoint                | responseCode | time |
      | validation-extensions   | 200          |300   |



