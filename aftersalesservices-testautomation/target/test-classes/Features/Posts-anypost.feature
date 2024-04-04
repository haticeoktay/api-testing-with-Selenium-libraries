@Gets
Feature: Posts generatecmac
  I want to be sure
  That the Posts endpoint is working as expected


  @tmsLink=01 @severity=critical
  @smoke @regression @prod
  Scenario: Response body has expected content for EPS_RWD
#    Given I do a post to the "part-number" endpoint
    Given the request "ecu-parts" part-number creates ecu parts with the parameter "EPS_RWD"
    And the returned status code is for post request: "200"
    Then the response "1" number "value" equal to "integer" "6" from the request into the size of "3" array
    Then the response "1" number "text" equal to "string" "Hardware" from the request into the size of "3" array
    Then the response "1" number "type" equal to "string" "hardware" from the request into the size of "3" array
    Then the response "1" number "did" equal to "string" "0xF191" from the request into the size of "3" array

  @tmsLink=02 @severity=critical
  @smoke @regression @prod
  Scenario: gives an error when the vehicleGeneration is not entered
    Given the request "ecu-parts" part-number creates ecu parts with the parameter ""
    Then the returned status code is for post request: "400"
    Then the returned message is for post request: "Unknown error."

  @tmsLink=03 @severity=critical
  @smoke @regression @prod
  Scenario: Response body has expected content for ACM
    Given the request "ecu-parts" part-number creates ecu parts with the parameter "ACM"
    And the returned status code is for post request: "200"
    Then the response "1" number "value" equal to "integer" "6" from the request into the size of "4" array
    Then the response "1" number "text" equal to "string" "Hardware" from the request into the size of "4" array
    Then the response "1" number "type" equal to "string" "hardware" from the request into the size of "4" array
    Then the response "1" number "did" equal to "string" "0xF191" from the request into the size of "4" array











