package es.urjc.realfood.clients.api.rest

import com.fasterxml.jackson.databind.ObjectMapper
import es.urjc.realfood.clients.application.RegisterClient
import es.urjc.realfood.clients.application.RegisterClientRequest
import es.urjc.realfood.clients.application.RegisterClientResponse
import es.urjc.realfood.clients.infrastructure.api.rest.RegisterRestController
import es.urjc.realfood.clients.infrastructure.api.security.JWTGeneratorService
import io.restassured.RestAssured
import io.restassured.module.mockmvc.RestAssuredMockMvc
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.web.server.LocalServerPort

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class RegisterRestControllerTest {

    @LocalServerPort
    var port = 0

    @MockBean
    lateinit var registerClient: RegisterClient

    lateinit var registerRestController: RegisterRestController

    private val objectMapper = ObjectMapper()
    private val jwtGeneratorService = JWTGeneratorService("1234", "realfood-auth")

    @BeforeAll
    fun setUp() {
        registerRestController = RegisterRestController(registerClient)

        RestAssured.port = Integer.parseInt(System.getProperty("port", "$port"))
        RestAssuredMockMvc.standaloneSetup(registerRestController)
    }

    protected fun validRegisterClientRequestJson(): String {
        return objectMapper.writeValueAsString(validRegisterClientRequest())
    }

    protected fun validRegisterClientRequest(): RegisterClientRequest {
        return RegisterClientRequest(
            name = "Cristofer",
            lastName = "Lopez",
            email = "cristofer@cristofer.es",
            password = "1234"
        )
    }

    protected fun validRegisterClientResponse(): RegisterClientResponse {
        return RegisterClientResponse(
            clientId = validUserId(),
            token = validJwt()
        )
    }

    protected fun validUserId(): String = "89a135b8-98dc-4e57-a22f-b5f99c6b1a99"

    protected fun validJwt(): String = jwtGeneratorService.generateJwt(validUserId())

}