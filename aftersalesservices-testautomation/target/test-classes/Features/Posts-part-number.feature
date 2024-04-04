@Gets
Feature: Posts part/number
  I want to be sure
  That the Posts endpoint is working as expected


  @tmsLink=01 @severity=critical
    @smoke @regression @prod
  Scenario: Response body has expected content
#    Given I do a post to the "part-number" endpoint
    Given the request part-number creates part number with the parameter "EPS_RWD" ,"1","1","1","A"
    And the returned status code is for post request: "200"
    Then the response "partNumber" equal to "C1_10601101110_AA" from the request for a single value

  @tmsLink=02 @severity=critical
  @smoke @regression @prod
  Scenario: Response body has expected content part number for bootloader
#    Given I do a post to the "part-number" endpoint
    Given the request part-number creates part number with the parameter "EPS_RWD" ,"6","1","1","A"
    And the returned status code is for post request: "200"
    Then the response "partNumber" equal to "C1_B0601101110_AA" from the request for a single value

  @tmsLink=03 @severity=critical
  @smoke @regression @prod
  Scenario: Response body has expected content part number for application
#    Given I do a post to the "part-number" endpoint
    Given the request part-number creates part number with the parameter "EPS_RWD" ,"3","1","1","A"
    And the returned status code is for post request: "200"
    Then the response "partNumber" equal to "C1_S0601101110_AA" from the request for a single value

  @tmsLink=04 @severity=critical
  @smoke @regression @prod
  Scenario: gives an error when the vehicleGeneration is not entered
    Given incomplete information is entered to the request with the parameters "EPS_RWD","1","","1","A"
    Then the returned status code is for post request: "400"
    Then the returned message is for post request: "Unknown error."

  @tmsLink=05 @severity=critical
  @smoke @regression @prod
  Scenario: gives an error when the partType is not entered
    Given incomplete information is entered to the request with the parameters "EPS_RWD","","1","1","A"
    And the returned status code is for post request: "400"
    Then the returned message is for post request: "Unknown error."

  @tmsLink=06 @severity=critical
  @smoke @regression @prod
  Scenario: gives an error when the hwAcronym is not entered
    Given incomplete information is entered to the request with the parameters "","1","1","1","A"
    And the returned status code is for post request: "400"
    Then the returned message is for post request: "Unknown error."

  @tmsLink=07 @severity=critical
  @smoke @regression @prod
  Scenario: gives an error when the vehiclePlatform is not entered
    Given incomplete information is entered to the request with the parameters "EPS_RWD","1","1","","A"
    And the returned status code is for post request: "400"
    Then the returned message is for post request: "Unknown error."

  @tmsLink=08 @severity=critical
  @smoke @regression @prod
  Scenario: gives an error when the revision is not entered
    Given incomplete information is entered to the request with the parameters "EPS_RWD","1","1","1",""
    And the returned status code is for post request: "200"
    Then the response "partNumber" equal to "C1_10601101110_A" from the request for a single value







