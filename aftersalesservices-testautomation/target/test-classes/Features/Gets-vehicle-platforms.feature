@Gets
Feature: Gets vehicle/platforms
  I want to be sure
  That the Gets endpoint is working as expected


  @tmsLink=01 @severity=critical
    @smoke @regression @prod
  Scenario: Verify status code returned is expected
    Given I do a get to the "vehicle-platforms" endpoint
    Then the returned status code is: "200"
    And the schema for the "vehicle-platforms" endpoint with "200" response code is correct


  @tmsLink=02 @severity=critical
  @smoke @regression @prod
  Scenario: Test for checking the response body
    Given I do a get to the "vehicle-platforms" endpoint
    And the response contains "1" items
    And the response have value "1" equal to text "C-SUV" for "targets" api

  @tmsLink=03 @severity=normal
    @smoke @regression
    @2 @3 @prod
  Scenario Outline: Verify amount of returned items is expected
    Given I do a get to the "<Endpoint>" endpoint
    Then the returned status code is: "200"
    And the response contains "<amount>" items
    Examples:
      | Endpoint          | amount |
      | vehicle-platforms | 1      |

  @tmsLink=04 @severity=normal
    @smoke @regression
    @2 @3 @prod
  Scenario Outline: test for return status code  less than a given time(milliseconds)
    Given I do a get to the "<Endpoint>" endpoint
    When the returned status code is: "<responseCode>" in time "<time>"
    Examples:
      | Endpoint            | responseCode | time |
      | vehicle-platforms   | 200          |300   |



