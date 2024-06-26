@Gets
Feature: Posts decoder
  I want to be sure
  That the Posts endpoint is working as expected


  @tmsLink=01 @severity=critical
    @smoke @regression @prod
  Scenario: Response body has expected content-it should be CSUV model
    Given the request decoder run with the parameters "C" ,"3b6dc000000000000000000000000000000000000000000000000000000000000000000000000000"
    And the returned status code is for post request: "200"
    Then the response 0 number "value" equal to "1" from the request for a single value
    Then the response 0 number "key" equal to "CSUV" from the request for a single value
    Then the response 0 number "code" equal to "VEHICLE_BODY_TYPE" from the request for a single value


  @tmsLink=02 @severity=critical
  @smoke @regression @prod
  Scenario: Response body has expected content-it should be pamukkale white color
    Given the request decoder run with the parameters "C" ,"0000000000000000000000000000000000000000000000000000000000000000000000"
    And the returned status code is for post request: "200"
    Then the response 13 number "value" equal to "1" from the request for a single value
    Then the response 13 number "key" equal to "PAMUKKALE_WHITE" from the request for a single value
    Then the response 13 number "code" equal to "VEHICLE_COLOR" from the request for a single value

  @tmsLink=03 @severity=critical
  @smoke @regression @prod
  Scenario: Response body has expected content-it should be oltu black white color
    Given the request decoder run with the parameters "C" ,"00000000000000000000000000000000000000000000000000000000000000000000"
    And the returned status code is for post request: "200"
    Then the response 13 number "value" equal to "4" from the request for a single value
    Then the response 13 number "key" equal to "OLTU_BLACK" from the request for a single value
    Then the response 13 number "code" equal to "VEHICLE_COLOR" from the request for a single value


  @tmsLink=04 @severity=critical
  @smoke @regression @prod
  Scenario: Response body has expected content-it should be battery short range
    Given the request decoder run with the parameters "C" ,"0000000000001E0103013F2001010000000000000000000000"
    And the returned status code is for post request: "200"
    Then the response 16 number "value" equal to "0" from the request for a single value
    Then the response 16 number "key" equal to "SHORT_RANGE" from the request for a single value
    Then the response 16 number "code" equal to "HARDWARE_BATTERY_TYPE" from the request for a single value

  @tmsLink=05 @severity=critical
  @smoke @regression @prod
  Scenario: Response body has expected content-it should be battery long range
    Given the request decoder run with the parameters "C" ,"3b6dc47023e7bf64f63000000000010100000000000000000000000000"
    And the returned status code is for post request: "200"
    Then the response 15 number "value" equal to "1" from the request for a single value
    Then the response 15 number "key" equal to "LONG_RANGE" from the request for a single value
    Then the response 15 number "code" equal to "HARDWARE_BATTERY_TYPE" from the request for a single value

  @tmsLink=06 @severity=critical
  @smoke @regression @prod
  Scenario: Response body has expected content-it should be adas package 1
    Given the request decoder run with the parameters "C" ,"3b6dc47023e7bf64f636cfa353c0d6a100000001010000000000"
    And the returned status code is for post request: "200"
    Then the response 10 number "value" equal to "1" from the request for a single value
    Then the response 10 number "key" equal to "ADAS_PKG_1" from the request for a single value
    Then the response 10 number "code" equal to "ADAS" from the request for a single value

  @tmsLink=07 @severity=critical
  @smoke @regression @prod
  Scenario: Response body has expected content-it should be adas package 2
    Given the request decoder run with the parameters "C" ,"3b6d00000000000000000000000000000000"
    And the returned status code is for post request: "200"
    Then the response 10 number "value" equal to "2" from the request for a single value
    Then the response 10 number "key" equal to "ADAS_PKG_2" from the request for a single value
    Then the response 10 number "code" equal to "ADAS" from the request for a single value

  @tmsLink=08 @severity=critical
  @smoke @regression @prod
  Scenario: Response body has expected content-it should be interior ligthing type is base steel
    Given the request decoder run with the parameters "C" ,"3b6dc47023e7b0000000000000000000000000000000"
    And the returned status code is for post request: "200"
    Then the response 1 number "value" equal to "5" from the request for a single value
    Then the response 1 number "key" equal to "BASE_STEEL" from the request for a single value
    Then the response 1 number "code" equal to "INTERIOR_LIGTHING_TYPE" from the request for a single value

  @tmsLink=09 @severity=critical
  @smoke @regression @prod
  Scenario: it should return invalid model error-model is invalid
#    Given duplicate items in ECUList with the parameters "12345678901234567" ,"Sample VarCodName","SampleCode","123","BCM","PartNumber1","123","BCM","PartNumber1"
    Given the request decoder run with the parameters "C1" ,"3b600100000000000000000000000000000000000000000000000000000000000000000000"
    Then the returned status code is for post request: "400"
    Then the returned message is for post request: "Invalid model value."

  @tmsLink=10 @severity=critical
  @smoke @regression @prod
  Scenario: it should return invalid varcod error-varcod is invalid
    Given the request decoder run with the parameters "C" ,"test-3b6dc47023000000000000000000000000000000000000000000000000000000000000000000000000000"
    Then the returned status code is for post request: "400"
    Then the returned message is for post request: "Invalid varcod value."


  @tmsLink=11 @severity=critical
  @smoke @regression @prod
  Scenario: Empty varcod value-Varcod value is null
    Given the request decoder run with the parameters "C" ,""
    And the returned status code is for post request: "400"
    Then the returned message is for post request: "Model varcod is null."

  @tmsLink=12 @severity=critical
  @smoke @regression @prod
  Scenario: Empty model value-Model value is null
    Given the request decoder run with the parameters "" ,"3b6000000000000000000000000000000000000000000000000000"
    And the returned status code is for post request: "400"
    Then the returned message is for post request: "Model value is null."

  @tmsLink=13 @severity=critical
  @smoke @regression @prod
  Scenario: Empty request
    Given the request is empty
    And the returned status code is for post request: "400"
    Then the returned message is for post request: "Model value is null."








