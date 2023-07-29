@BankAccount
Feature: Bank Account creation

  Scenario: Bank account creation with correct body
    When user hits the bank account creation API with correct body and token
    Then user should hit get single account API
    Then user should verify that account was created and HTTP request should be "200"