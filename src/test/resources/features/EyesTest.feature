Feature: EyesTest

  Scenario: KhaiSite test
    When I open web-site
    And I launch Eyes tool
      | testName | appName |
      | testName | app     |
    And I take screenshot via Eyes tool 1