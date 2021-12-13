package es.urjc.realfood.clients.infrastructure.api.rest

import com.fasterxml.jackson.databind.ObjectMapper
import es.urjc.realfood.clients.application.FindAllByClientIdOrders
import es.urjc.realfood.clients.application.FindAllByClientIdOrdersRequest
import es.urjc.realfood.clients.application.FindAllByClientIdOrdersResponse
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
abstract class OrderRestControllerTest {

    @LocalServerPort
    var port = 0

    @MockBean
    lateinit var findAllByClientIdOrders: FindAllByClientIdOrders

    @MockBean
    lateinit var jwtValidatorService: JWTValidatorService

    lateinit var orderRestController: OrderRestController

    private val objectMapper = ObjectMapper()
    private val jwtGeneratorService = JWTGeneratorService("1234", "realfood-auth")

    @BeforeAll
    fun setUp() {
        orderRestController = OrderRestController(
            findAllByClientIdOrders = findAllByClientIdOrders,
            jwtValidatorService = jwtValidatorService
        )

        RestAssured.port = Integer.parseInt(System.getProperty("port", "$port"))
        RestAssuredMockMvc.standaloneSetup(orderRestController)
    }

    protected fun validFindAllRequest(): FindAllByClientIdOrdersRequest {
        return FindAllByClientIdOrdersRequest(
            clientId = validUserId()
        )
    }

    protected fun validFindAllResponse(): FindAllByClientIdOrdersResponse {
        return FindAllByClientIdOrdersResponse(
            clientId = validUserId(),
            orders = emptyList()
        )
    }

    protected fun validCartId(): String = "89a135b8-98dc-4e57-a22f-b5f99c6b1a99"

    protected fun validUserId(): String = "89a135b8-98dc-4e57-a22f-b5f99c6b1a11"

    protected fun validOrderId(): String = "89a135b8-98dc-4e57-a22f-b5f99c6b1a22"

    protected fun validJwt(): String = jwtGeneratorService.generateJwt(validUserId())

}