Feature: BlazeDemo

    Scenario: Booking a flight from Mexico City to Cairo
        Given I am at 'https://blazedemo.com'
        And I choose my departure city 'Mexico City' and my destination city 'Cairo'
        And I click Find Flights button
        And Page should say 'Flights from Mexico City to Cairo'
        And I choose a flight and click the button
        And I fill in my name as 'Manco'
        And I fill in my city as 'Candieira City'
        And I click Purchase Flight button
        Then Page title should be 'BlazeDemo Confirmation'