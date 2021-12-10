package es.urjc.realfood.clients.integration

import es.urjc.realfood.clients.infrastructure.api.rest.ClientRestControllerTest
import es.urjc.realfood.clients.application.FindByIdClientResponse
import es.urjc.realfood.clients.application.LoginClientResponse
import es.urjc.realfood.clients.domain.exception.ClientNotFoundException
import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyMap

class ClientRestControllerIntegrationTest : ClientRestControllerTest() {

    @Test
    fun `given profile endpoint when find me then return status ok`() {
        `when`(jwtValidatorService.getSubjectFromHeaders(anyMap()))
            .thenReturn(validUserId())
        `when`(findByIdClient(validFindByIdClientRequest()))
            .thenReturn(validFindByIdClientResponse())

        RestAssured.given()
            .request()
            .header("Authorization", "Bearer ${validJwt()}")
            .body(validFindByIdClientRequestJson())
            .contentType(ContentType.JSON)
            .`when`()
            .get("/api/me")
            .then()
            .assertThat()
            .statusCode(200)
            .body("id", Matchers.equalTo(validUserId()))
            .body("name", Matchers.equalTo("Cristofer"))
            .body("lastName", Matchers.equalTo("Lopez"))
            .body("email", Matchers.equalTo("cristofer@cristofer.es"))
            .extract().`as`(FindByIdClientResponse::class.java)
    }

    @Test
    fun `given profile endpoint when client not found exception then return 404 status code`() {
        `when`(jwtValidatorService.getSubjectFromHeaders(anyMap()))
            .thenReturn(validUserId())
        `when`(findByIdClient(validFindByIdClientRequest()))
            .thenThrow(ClientNotFoundException("Client not found"))

        RestAssured.given()
            .request()
            .header("Authorization", "Bearer ${validJwt()}")
            .body(validFindByIdClientRequestJson())
            .contentType(ContentType.JSON)
            .`when`()
            .get("/api/me")
            .then()
            .assertThat()
            .statusCode(404)
            .body("reason", Matchers.equalTo("Client not found"))
    }


    @Test
    fun `given unsubscribe endpoint when delete me then return status ok`() {
        `when`(jwtValidatorService.getSubjectFromHeaders(anyMap()))
            .thenReturn(validUserId())

        RestAssured.given()
            .request()
            .header("Authorization", "Bearer ${validJwt()}")
            .body(validDeleteClientRequestJson())
            .contentType(ContentType.JSON)
            .`when`()
            .delete("/api/unsubscribe")
            .then()
            .assertThat()
            .statusCode(200)
    }

    @Test
    fun `given unsubscribe endpoint when client not found exception then return 404 status code`() {
        `when`(jwtValidatorService.getSubjectFromHeaders(anyMap()))
            .thenReturn(validUserId())
        `when`(deleteClient(validDeleteClientRequest()))
            .thenThrow(ClientNotFoundException("Client not found"))

        RestAssured.given()
            .request()
            .header("Authorization", "Bearer ${validJwt()}")
            .body(validDeleteClientRequestJson())
            .contentType(ContentType.JSON)
            .`when`()
            .delete("/api/unsubscribe")
            .then()
            .assertThat()
            .statusCode(404)
            .body("reason", Matchers.equalTo("Client not found"))
    }


    @Test
    fun `given login endpoint when login then return status ok`() {
        `when`(loginClient(validLoginClientRequest()))
            .thenReturn(validLoginClientResponse())

        RestAssured.given()
            .request()
            .body(validLoginClientRequestJson())
            .contentType(ContentType.JSON)
            .`when`()
            .post("/api/login")
            .then()
            .assertThat()
            .statusCode(200)
            .body("clientId", Matchers.equalTo(validUserId()))
            .extract().`as`(LoginClientResponse::class.java)
    }

    @Test
    fun `given login endpoint when client not found exception then return 404 status code`() {
        `when`(loginClient(validLoginClientRequest()))
            .thenThrow(ClientNotFoundException("Client not found"))

        RestAssured.given()
            .request()
            .body(validLoginClientRequestJson())
            .contentType(ContentType.JSON)
            .`when`()
            .post("/api/login")
            .then()
            .assertThat()
            .statusCode(404)
            .body("reason", Matchers.equalTo("Client not found"))
    }

    @Test
    fun `given login endpoint when illegal argument exception then return 400 status code`() {
        `when`(loginClient(validLoginClientRequest()))
            .thenThrow(IllegalArgumentException("Illegal"))

        RestAssured.given()
            .request()
            .body(validLoginClientRequestJson())
            .contentType(ContentType.JSON)
            .`when`()
            .post("/api/login")
            .then()
            .assertThat()
            .statusCode(400)
            .body("reason", Matchers.equalTo("Illegal"))
    }

}