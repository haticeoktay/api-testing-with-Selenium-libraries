@Gets
Feature: Gets part/types
  I want to be sure
  That the Gets endpoint is working as expected


  @tmsLink=01 @severity=critical
    @smoke @regression @prod
  Scenario: Verify status code returned is expected
    Given I do a get to the "part-types" endpoint
    Then the returned status code is: "200"
    And the schema for the "part-types" endpoint with "200" response code is correct

  @tmsLink=02 @severity=critical
  @smoke @regression @prod
  Scenario: Test for checking the response body
    Given I do a get to the "part-types" endpoint
    And the response contains "6" items
    And the response have value "6" equal to text "Hardware" for "entity-types" api

  @tmsLink=03 @severity=normal
    @smoke @regression
    @2 @3 @prod
  Scenario Outline: Verify amount of returned items is expected
    Given I do a get to the "<Endpoint>" endpoint
    Then the returned status code is: "200"
    And the response contains "<amount>" items
    Examples:
      | Endpoint    | amount |
      | part-types   | 6     |

  @tmsLink=04 @severity=normal
    @smoke @regression
    @2 @3 @prod
  Scenario Outline: test for return status code  less than a given time(milliseconds)
    Given I do a get to the "<Endpoint>" endpoint
    When the returned status code is: "<responseCode>" in time "<time>"
    Examples:
      | Endpoint    | responseCode | time |
      | part-types   | 200          |300    |



