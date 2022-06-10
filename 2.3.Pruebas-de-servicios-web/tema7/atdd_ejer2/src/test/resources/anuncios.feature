Feature: anuncios end-point

    Background:
    * url 'http://localhost:8081/anuncios'
    * configure logPrettyRequest = true
    * configure logPrettyResponse = true

    Scenario: Crear, recibir y borrar anuncio
        
        # CREAMOS UN NUEVO ANUNCIO

        * def anuncio = { nombre: 'Juan', asunto: 'Vendo coche', comentario: "Vendo Fiat 500"}
        * def expected_response = { id: '#number', nombre: 'Juan', asunto: 'Vendo coche', comentario: "Vendo Fiat 500"}

        Given path ''
        And request anuncio
        When method post
        Then status 201
        And match response == expected_response

        # COMPROBAMOS QUE PODEMOS RECUPERARLO

        * def id = response.id

        Given path '', id
        When method get
        Then status 200
        And match response == expected_response

        # # BORRAMOS EL ANUNCIO

        Given path '', id
        When method delete
        Then status 200
        And match response == expected_response

        # COMPROBAMOS QUE SE HA BORRADO CORRECTAMENTE

        Given path '', id
        When method get
        Then status 404

