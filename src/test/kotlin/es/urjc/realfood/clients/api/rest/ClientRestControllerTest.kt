package es.urjc.realfood.clients.api.rest

import com.fasterxml.jackson.databind.ObjectMapper
import es.urjc.realfood.clients.application.*
import es.urjc.realfood.clients.infrastructure.api.rest.ClientRestController
import es.urjc.realfood.clients.infrastructure.api.security.JWTGeneratorService
import es.urjc.realfood.clients.infrastructure.api.security.JWTValidatorService
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
abstract class ClientRestControllerTest {

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

    private val objectMapper = ObjectMapper()
    private val jwtGeneratorService = JWTGeneratorService("1234", "realfood-auth")

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

    protected fun validFindByIdClientRequestJson(): String {
        return objectMapper.writeValueAsString(validFindByIdClientRequest())
    }

    protected fun validFindByIdClientRequest(): FindByIdClientRequest {
        return FindByIdClientRequest(
            id = validUserId()
        )
    }

    protected fun validFindByIdClientResponse(): FindByIdClientResponse {
        return FindByIdClientResponse(
            id = validUserId(),
            name = "Cristofer",
            lastName = "Lopez",
            email = "cristofer@cristofer.es"
        )
    }

    protected fun validDeleteClientRequestJson(): String {
        return objectMapper.writeValueAsString(validDeleteClientRequest())
    }

    protected fun validDeleteClientRequest(): DeleteClientRequest {
        return DeleteClientRequest(
            id = validUserId()
        )
    }

    protected fun validLoginClientRequestJson(): String {
        return objectMapper.writeValueAsString(validLoginClientRequest())
    }

    protected fun validLoginClientRequest(): LoginClientRequest {
        return LoginClientRequest(
            email = "cristofer@cristofer.es",
            password = "1234"
        )
    }

    protected fun validLoginClientResponse(): LoginClientResponse {
        return LoginClientResponse(
            clientId = validUserId(),
            token = validJwt()
        )
    }

    protected fun validUserId(): String = "89a135b8-98dc-4e57-a22f-b5f99c6b1a99"

    protected fun validJwt(): String = jwtGeneratorService.generateJwt(validUserId())

}