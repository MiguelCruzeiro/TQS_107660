@calc_sample
Feature: Basic Arithmetic

  Background: A Calculator
    Given a calculator I just turned on

  Scenario: Addition
    When I add 4 and 5
    Then the result is 9

  Scenario: Substraction
    When I substract 7 to 2 
    Then the result is 5

  Scenario: Multiplication
    When I multiply 2 and 3
    Then the result is 6

  Scenario: Division
    When I divide 6 by 3
    Then the result is 2

  Scenario: Division by 0
    When I divide 6 by 0
    Then the result is Infinity

  Scenario: Division
    When I divide 0 by 4
    Then the result is 0

  Scenario Outline: Several additions
    When I add <a> and <b>
    Then the result is <c>

  Examples: Single digits
    | a | b | c  |
    | 1 | 2 | 3  |
    | 3 | 7 | 10 |