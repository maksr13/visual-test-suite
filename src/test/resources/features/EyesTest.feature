Feature: EyesTest

  Scenario: Google test Ukrainian
    When I open web-site https://www.google.com/?hl=ru
    And I click on some text
    And I launch Eyes tool
      | appName               | testName              |
      | Google test Ukrainian | Google test Ukrainian |
    And I take screenshot via Eyes tool Ukrainian
    And I close Eyes

  Scenario: Google test Russian
    When I open web-site https://www.google.com/?hl=ru
    And I click on some text
    And I launch Eyes tool
      | appName             | testName            |
      | Google test Russian | Google test Russian |
    And I take screenshot via Eyes tool Russian
    And I close Eyes

  Scenario: Google test English
    When I open web-site https://www.google.com/?hl=en
    And I click on some text
    And I launch Eyes tool
      | appName             | testName            |
      | Google test English | Google test English |
    And I take screenshot via Eyes tool English
    And I close Eyes
