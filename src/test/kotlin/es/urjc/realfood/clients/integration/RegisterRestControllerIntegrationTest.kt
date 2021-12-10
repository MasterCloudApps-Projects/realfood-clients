package es.urjc.realfood.clients.integration

import es.urjc.realfood.clients.api.rest.RegisterRestControllerTest
import es.urjc.realfood.clients.application.RegisterClientResponse
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`

class RegisterRestControllerIntegrationTest : RegisterRestControllerTest() {

    @Test
    fun `given register endpoint when register then return status ok`() {
        `when`(registerClient(validRegisterClientRequest()))
            .thenReturn(validRegisterClientResponse())

        given()
            .request()
            .body(validRegisterClientRequestJson())
            .contentType(ContentType.JSON)
            .`when`()
            .post("/api/clients")
            .then()
            .assertThat()
            .statusCode(200)
            .body("clientId", equalTo(validUserId()))
            .extract().`as`(RegisterClientResponse::class.java)
    }

    @Test
    fun `given register endpoint when throw illegal argument exception then return 400 status code`() {
        `when`(registerClient(validRegisterClientRequest()))
            .thenThrow(IllegalArgumentException("Illegal"))

        given()
            .request()
            .body(validRegisterClientRequestJson())
            .contentType(ContentType.JSON)
            .`when`()
            .post("/api/clients")
            .then()
            .assertThat()
            .statusCode(400)
            .body("reason", equalTo("Illegal"))
    }

}