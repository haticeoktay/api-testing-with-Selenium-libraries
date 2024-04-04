@Gets
Feature: Gets ecus
  I want to be sure
  That the Gets endpoint is working as expected


  @tmsLink=01 @severity=critical
    @smoke @regression @prod
  Scenario: Verify status code returned is expected
    Given I do a get to the "ecus" endpoint
    Then the returned status code is: "200"
    And the schema for the "ecus" endpoint with "200" response code is correct

  @tmsLink=02 @severity=critical
  @smoke @regression @prod
  Scenario: Test for checking the response body
    Given I do a get to the "ecus" endpoint
    And the response contains "3" items
    And the response have value "ACM" equal to text "Airbag Control Module" for "ecus" api
    Then the response 0 number "logicalAddresses" equal to "0x1770" from the request for a single value

  @tmsLink=02 @severity=normal
    @smoke @regression
    @2 @3 @prod
  Scenario Outline: Verify amount of returned items is expected
    Given I do a get to the "<Endpoint>" endpoint
    Then the returned status code is: "200"
    And the response contains "<amount>" items
    Examples:
      | Endpoint | amount |
      | ecus     | 3     |

  @tmsLink=03 @severity=normal
    @smoke @regression
    @2 @3 @prod
  Scenario Outline: test for return status code  less than a given time(milliseconds)
    Given I do a get to the "<Endpoint>" endpoint
    When the returned status code is: "<responseCode>" in time "<time>"
    Examples:
      | Endpoint | responseCode | time |
      | ecus     | 200          |300    |



