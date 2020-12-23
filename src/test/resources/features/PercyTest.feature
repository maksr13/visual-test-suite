Feature: PercyTest

  Scenario: Google test Ukrainian
    And I launch Percy tool
    When I open web-site https://www.google.com/?hl=ru
    And I click on some text
    And I take screenshot via Percy tool Ukrainian

  Scenario: Google test Russian
    And I launch Percy tool
    When I open web-site https://www.google.com/?hl=ru
    And I click on some text
    And I take screenshot via Percy tool Russian

  Scenario: Google test English
    And I launch Percy tool
    When I open web-site https://www.google.com/?hl=en
    And I click on some text
    And I take screenshot via Percy tool English
