package es.urjc.realfood.clients.integration

import es.urjc.realfood.clients.application.FindAllByClientIdOrders
import es.urjc.realfood.clients.application.FindAllByClientIdOrdersResponse
import es.urjc.realfood.clients.application.FindByIdAndClientIdOrder
import es.urjc.realfood.clients.application.FindByIdAndClientIdOrderResponse
import es.urjc.realfood.clients.domain.ClientObjectProvider.Companion.validClientIdString
import es.urjc.realfood.clients.domain.ClientObjectProvider.Companion.validJwt
import es.urjc.realfood.clients.domain.OrderObjectProvider.Companion.validOrderIdString
import es.urjc.realfood.clients.domain.exception.EntityNotFoundException
import es.urjc.realfood.clients.infrastructure.api.rest.OrderRestController
import es.urjc.realfood.clients.infrastructure.api.rest.OrderRestControllerTest
import es.urjc.realfood.clients.infrastructure.api.security.JWTValidatorService
import io.restassured.RestAssured
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
class OrderRestControllerIntegrationTest : OrderRestControllerTest() {

    @LocalServerPort
    var port = 0

    @MockBean
    lateinit var findAllByClientIdOrders: FindAllByClientIdOrders

    @MockBean
    lateinit var findByIdAndClientIdOrder: FindByIdAndClientIdOrder

    @MockBean
    lateinit var jwtValidatorService: JWTValidatorService

    lateinit var orderRestController: OrderRestController

    @BeforeAll
    fun setUp() {
        orderRestController = OrderRestController(
            findAllByClientIdOrders = findAllByClientIdOrders,
            findByIdAndClientIdOrder = findByIdAndClientIdOrder,
            jwtValidatorService = jwtValidatorService
        )

        RestAssured.port = Integer.parseInt(System.getProperty("port", "$port"))
        RestAssuredMockMvc.standaloneSetup(orderRestController)
    }

    @Test
    fun `given find all user orders endpoint when get orders then return status ok`() {
        `when`(jwtValidatorService.getSubjectFromHeaders(anyMap()))
            .thenReturn(validClientIdString())

        `when`(findAllByClientIdOrders(validFindAllRequest()))
            .thenReturn(validFindAllResponse())

        RestAssured.given()
            .request()
            .header("Authorization", "Bearer ${validJwt()}")
            .`when`()
            .get("/api/clients/me/orders")
            .then()
            .assertThat()
            .statusCode(200)
            .body("clientId", Matchers.equalTo(validClientIdString()))
            .extract().`as`(FindAllByClientIdOrdersResponse::class.java)
    }

    @Test
    fun `given find user order endpoint when get order then return status ok`() {
        `when`(jwtValidatorService.getSubjectFromHeaders(anyMap()))
            .thenReturn(validClientIdString())

        `when`(findByIdAndClientIdOrder(validFindRequest()))
            .thenReturn(validFindResponse())

        RestAssured.given()
            .request()
            .header("Authorization", "Bearer ${validJwt()}")
            .`when`()
            .get("/api/clients/me/orders/${validOrderIdString()}")
            .then()
            .assertThat()
            .statusCode(200)
            .body("orderId", Matchers.equalTo(validOrderIdString()))
            .body("status", Matchers.equalTo("PENDING"))
            .extract().`as`(FindByIdAndClientIdOrderResponse::class.java)
    }

    @Test
    fun `given find user order endpoint when not found user or order order then return 404 status code`() {
        `when`(jwtValidatorService.getSubjectFromHeaders(anyMap()))
            .thenReturn(validClientIdString())

        `when`(findByIdAndClientIdOrder(validFindRequest()))
            .thenThrow(EntityNotFoundException("Order not found"))

        RestAssured.given()
            .request()
            .header("Authorization", "Bearer ${validJwt()}")
            .`when`()
            .get("/api/clients/me/orders/${validOrderIdString()}")
            .then()
            .assertThat()
            .statusCode(404)
            .body("reason", Matchers.equalTo("Order not found"))
    }

}