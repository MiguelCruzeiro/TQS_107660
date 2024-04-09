Feature: Trips

    Scenario: Booking a trip from Mexico City to Cairo
        Given I am at 'http://localhost:4200'
        And I choose my departure city 'Porto' and my destination city 'Lisboa'
        And I click Get Trips button
        And I choose a trip and click the button
        And I fill in my name as 'Miguel'
        And I fill in my number of seats as 1
        And I click Submit Reservation button
        Then Page title should be 'Reservation Confirmation'