Feature: CustomVisualTestingToolTest

  Scenario: Google test Ukrainian
    When I open web-site https://www.google.com/?hl=uk
    And I click on some text
    And I launch Custom Visual Testing tool
      | feature                 | scenario              |
      | CustomVisualTestingTool | Google test Ukrainian |
    And I take screenshot via Custom Visual Testing tool Ukrainian

  Scenario: Google test Russian
    When I open web-site https://www.google.com/?hl=ru
    And I click on some text
    And I launch Custom Visual Testing tool
      | feature                 | scenario            |
      | CustomVisualTestingTool | Google test Russian |
    And I take screenshot via Custom Visual Testing tool Russian

  Scenario: Google test English
    When I open web-site https://www.google.com/?hl=en
    And I click on some text
    And I launch Custom Visual Testing tool
      | feature                 | scenario            |
      | CustomVisualTestingTool | Google test English |
    And I take screenshot via Custom Visual Testing tool English
