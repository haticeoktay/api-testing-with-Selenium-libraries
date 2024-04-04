@Gets
Feature: Posts supplier/license
  I want to be sure
  That the Posts endpoint is working as expected


  @tmsLink=01 @severity=critical
  @smoke @regression @prod
  Scenario: Response body has expected content
    Given the request "software-history" software-history show the history of the software for the "ACM" and "4"
    And the returned status code is for post request: "200"
    Then the response "status" equal to text "success"

  @tmsLink=02 @severity=critical
  @smoke @regression @prod
  Scenario: PartType Info is missing
    Given the request "software-history" software-history show the history of the software for the "ACM" and ""
    And the returned status code is for post request: "400"
    Then the response "message" equal to text "Part type information cannot be blank."

  @tmsLink=03 @severity=critical
  @smoke @regression @prod
  Scenario: hwAcronym Info is missing
    Given the request "software-history" software-history show the history of the software for the "" and "4"
    And the returned status code is for post request: "400"
    Then the response "message" equal to text "Hardware acronym information cannot be blank."

  @tmsLink=04 @severity=critical
  @smoke @regression @prod
  Scenario: hwAcronym and PartType Infos are missing
    Given the request "software-history" software-history show the history of the software for the "" and ""
    And the returned status code is for post request: "400"
    Then the response "message" equal to text "Hardware acronym information cannot be blank."
    Then the response "message2" equal to text "Part type information cannot be blank."

  @tmsLink=05 @severity=critical
  @smoke @regression @prod
  Scenario: Invalid part type value
    Given the request "software-history" software-history show the history of the software for the "ACM" and "asasas"
    And the returned status code is for post request: "400"
    Then the response "message" equal to text "Invalid part type value."

  @tmsLink=06 @severity=critical
  @smoke @regression @prod
  Scenario: Invalid hardware acronym value
    Given the request "software-history" software-history show the history of the software for the "ACMssdsdsdd" and "4"
    And the returned status code is for post request: "400"
    Then the response "message" equal to text "Invalid hardware acronym value."













