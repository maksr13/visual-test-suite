Feature: CustomVisualTestingTool

  Scenario: KhaiSite test 1
    When I open web-site
    And I launch Custom Visual Testing tool
      | feature | scenario |
      | feature | test1    |
    And I take screenshot via Custom Visual Testing tool 1

  Scenario: KhaiSite test 2
    When I open web-site
    And I launch Custom Visual Testing tool
      | feature | scenario |
      | feature | test2    |
    And I take screenshot via Custom Visual Testing tool 1
