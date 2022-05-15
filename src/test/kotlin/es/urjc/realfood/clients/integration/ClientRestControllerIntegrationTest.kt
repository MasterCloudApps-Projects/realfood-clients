package es.urjc.realfood.clients.integration

import es.urjc.realfood.clients.application.*
import es.urjc.realfood.clients.domain.ClientObjectProvider.Companion.validClientIdString
import es.urjc.realfood.clients.domain.ClientObjectProvider.Companion.validJwt
import es.urjc.realfood.clients.domain.exception.EntityNotFoundException
import es.urjc.realfood.clients.infrastructure.api.rest.ClientRestController
import es.urjc.realfood.clients.infrastructure.api.rest.ClientRestControllerTest
import es.urjc.realfood.clients.infrastructure.api.security.JWTValidatorService
import io.restassured.RestAssured
import io.restassured.http.ContentType
import io.restassured.module.mockmvc.RestAssuredMockMvc
import org.hamcrest.Matchers
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyMap
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.web.server.LocalServerPort

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ClientRestControllerIntegrationTest : ClientRestControllerTest() {

    @LocalServerPort
    var port = 0

    @MockBean
    lateinit var findByIdClient: FindByIdClient

    @MockBean
    lateinit var loginClient: LoginClient

    @MockBean
    lateinit var deleteClient: DeleteClient

    @MockBean
    lateinit var jwtValidatorService: JWTValidatorService

    lateinit var clientRestController: ClientRestController

    @BeforeAll
    fun setUp() {
        clientRestController = ClientRestController(
            findByIdClient = findByIdClient,
            deleteClient = deleteClient,
            loginClient = loginClient,
            jwtService = jwtValidatorService
        )

        RestAssured.port = Integer.parseInt(System.getProperty("port", "$port"))
        RestAssuredMockMvc.standaloneSetup(clientRestController)
    }

    @Test
    fun `given profile endpoint when find me then return status ok`() {
        `when`(jwtValidatorService.getSubjectFromHeaders(anyMap()))
            .thenReturn(validClientIdString())

        `when`(findByIdClient(validFindByIdClientRequest()))
            .thenReturn(validFindByIdClientResponse())

        RestAssured.given()
            .request()
            .header("Authorization", "Bearer ${validJwt()}")
            .body(validFindByIdClientRequestJson())
            .contentType(ContentType.JSON)
            .`when`()
            .get("/api/clients/me")
            .then()
            .assertThat()
            .statusCode(200)
            .body("id", Matchers.equalTo(validClientIdString()))
            .body("name", Matchers.equalTo("Cristofer"))
            .body("lastName", Matchers.equalTo("Lopez"))
            .body("email", Matchers.equalTo("cristofer@cristofer.es"))
            .extract().`as`(FindByIdClientResponse::class.java)
    }

    @Test
    fun `given profile endpoint when client not found exception then return 404 status code`() {
        `when`(jwtValidatorService.getSubjectFromHeaders(anyMap()))
            .thenReturn(validClientIdString())

        `when`(findByIdClient(validFindByIdClientRequest()))
            .thenThrow(EntityNotFoundException("Client not found"))

        RestAssured.given()
            .request()
            .header("Authorization", "Bearer ${validJwt()}")
            .body(validFindByIdClientRequestJson())
            .contentType(ContentType.JSON)
            .`when`()
            .get("/api/clients/me")
            .then()
            .assertThat()
            .statusCode(404)
            .body("reason", Matchers.equalTo("Client not found"))
    }

    @Test
    fun `given unsubscribe endpoint when delete me then return status ok`() {
        `when`(jwtValidatorService.getSubjectFromHeaders(anyMap()))
            .thenReturn(validClientIdString())

        RestAssured.given()
            .request()
            .header("Authorization", "Bearer ${validJwt()}")
            .body(validDeleteClientRequestJson())
            .contentType(ContentType.JSON)
            .`when`()
            .delete("/api/clients/me")
            .then()
            .assertThat()
            .statusCode(200)
    }

    @Test
    fun `given unsubscribe endpoint when client not found exception then return 404 status code`() {
        `when`(jwtValidatorService.getSubjectFromHeaders(anyMap()))
            .thenReturn(validClientIdString())

        `when`(deleteClient(validDeleteClientRequest()))
            .thenThrow(EntityNotFoundException("Client not found"))

        RestAssured.given()
            .request()
            .header("Authorization", "Bearer ${validJwt()}")
            .body(validDeleteClientRequestJson())
            .contentType(ContentType.JSON)
            .`when`()
            .delete("/api/clients/me")
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
            .post("/api/sign-in")
            .then()
            .assertThat()
            .statusCode(200)
            .body("clientId", Matchers.equalTo(validClientIdString()))
            .extract().`as`(LoginClientResponse::class.java)
    }

    @Test
    fun `given login endpoint when client not found exception then return 404 status code`() {
        `when`(loginClient(validLoginClientRequest()))
            .thenThrow(EntityNotFoundException("Client not found"))

        RestAssured.given()
            .request()
            .body(validLoginClientRequestJson())
            .contentType(ContentType.JSON)
            .`when`()
            .post("/api/sign-in")
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
            .post("/api/sign-in")
            .then()
            .assertThat()
            .statusCode(400)
            .body("reason", Matchers.equalTo("Illegal"))
    }

}