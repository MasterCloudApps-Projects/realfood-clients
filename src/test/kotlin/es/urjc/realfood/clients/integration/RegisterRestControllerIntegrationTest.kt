package es.urjc.realfood.clients.integration

import es.urjc.realfood.clients.application.RegisterClient
import es.urjc.realfood.clients.application.RegisterClientResponse
import es.urjc.realfood.clients.domain.ClientObjectProvider
import es.urjc.realfood.clients.infrastructure.api.rest.RegisterRestController
import es.urjc.realfood.clients.infrastructure.api.rest.RegisterRestControllerTest
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import io.restassured.module.mockmvc.RestAssuredMockMvc
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito.`when`
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.web.server.LocalServerPort

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RegisterRestControllerIntegrationTest : RegisterRestControllerTest() {

    @LocalServerPort
    var port = 0

    @MockBean
    lateinit var registerClient: RegisterClient

    lateinit var registerRestController: RegisterRestController

    @BeforeAll
    fun setUp() {
        registerRestController = RegisterRestController(registerClient)

        RestAssured.port = Integer.parseInt(System.getProperty("port", "$port"))
        RestAssuredMockMvc.standaloneSetup(registerRestController)
    }

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
            .body("clientId", equalTo(ClientObjectProvider.validClientIdString()))
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