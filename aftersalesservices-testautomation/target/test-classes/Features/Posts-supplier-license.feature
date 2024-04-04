@Gets
Feature: Posts supplier/license
  I want to be sure
  That the Posts endpoint is working as expected


  @tmsLink=01 @severity=critical
  @smoke @regression @prod
  Scenario: Response body has expected content
    Given the request "supplier-license" supplier-license creates the licenses and send the email
    And the returned status code is for post request: "200"
    Then the returned message is for post request: "License information sent to your e-mail address."













