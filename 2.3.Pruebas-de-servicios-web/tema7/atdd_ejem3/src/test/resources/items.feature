Feature: Items end-point

    Background:
    * url 'http://localhost:8081'
    * configure logPrettyRequest = true
    * configure logPrettyResponse = true

    Scenario: create and retrieve a item

        Given path 'items', ''
        And request { description: 'Leche', checked: true }
        When method post
        Then status 201
        And match response == { id: '#number', description: 'Leche', checked: true }