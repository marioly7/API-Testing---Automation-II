Feature: Create user and CRUD projects
  Scenario: Create user and obtain token
    Given I don't require authentication
    When I send a POST request to url "/api/user.json" with the following body
    """
    {
      "Email": "mariolyvvvfff@gmail.com",
      "FullName": "Marioly Vargas",
      "Password": "12345-Test"
    }
    """
    Then the response status should be 200
    And the attribute "Email" should be "mariolyvvvfff@gmail.com"
    And the attribute "FullName" should be "Marioly Vargas"
    And save the "Email" attribute in the variable "Email"

  Scenario: CRUD projects with user authentication
    Given I use the token of Todoly
    When I send a POST request to url "/api/projects.json" with the following body
    """
    {
        "Content": "MOLLYProject",
        "Icon": 5
    }
    """
    Then the response status should be 200
    And the attribute "Content" should be "MOLLYProject"
    And save the "Id" attribute in the variable "$ID_PROJECT"
    When I send a PUT request to url "/api/projects/$ID_PROJECT.json" with the following body
    """
    {
      "Content":"MOLLYUPDATEDtest1"
    }
    """
    Then the response status should be 200
    And the attribute "Content" should be "MOLLYUPDATEDtest1"
    And save the "Id" attribute in the variable "$ID_PROJECT"
    When I send a DELETE request to url "/api/projects/$ID_PROJECT.json" with the following body
    """
    """
    Then the response status should be 200
    And the attribute "Content" should be "MOLLYUPDATEDtest1"

