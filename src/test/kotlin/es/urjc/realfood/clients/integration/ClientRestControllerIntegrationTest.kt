package es.urjc.realfood.clients.integration

import es.urjc.realfood.clients.api.rest.ClientRestControllerTest
import es.urjc.realfood.clients.application.FindByIdClientResponse
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

}