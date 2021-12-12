package es.urjc.realfood.clients.infrastructure.api.rest

import com.fasterxml.jackson.databind.ObjectMapper
import es.urjc.realfood.clients.application.*
import es.urjc.realfood.clients.application.AddItemToCartRequest
import es.urjc.realfood.clients.application.DeleteItemFromCartRequest
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
abstract class CartRestControllerTest {

    @LocalServerPort
    var port = 0

    @MockBean
    lateinit var findByClientIdCart: FindByClientIdCart

    @MockBean
    lateinit var addItemToCart: AddItemToCart

    @MockBean
    lateinit var clearCart: ClearCart

    @MockBean
    lateinit var deleteItemFromCart: DeleteItemFromCart

    @MockBean
    lateinit var checkoutCart: CheckoutCart

    @MockBean
    lateinit var jwtValidatorService: JWTValidatorService

    lateinit var cartRestController: CartRestController

    private val objectMapper = ObjectMapper()
    private val jwtGeneratorService = JWTGeneratorService("1234", "realfood-auth")

    @BeforeAll
    fun setUp() {
        cartRestController = CartRestController(
            findByClientIdCart = findByClientIdCart,
            addItemToCart = addItemToCart,
            clearCart = clearCart,
            deleteItemFromCart = deleteItemFromCart,
            checkoutCart = checkoutCart,
            jwtValidatorService = jwtValidatorService
        )

        RestAssured.port = Integer.parseInt(System.getProperty("port", "$port"))
        RestAssuredMockMvc.standaloneSetup(cartRestController)
    }

    protected fun validFindByClientIdCartRequest(): FindByClientIdCartRequest {
        return FindByClientIdCartRequest(
            clientId = validUserId()
        )
    }

    protected fun validFindByClientIdCartResponse(): FindByClientIdCartResponse {
        return FindByClientIdCartResponse(
            cartId = validCartId(),
            items = emptyList()
        )
    }

    protected fun validClearCartRequest(): ClearCartRequest {
        return ClearCartRequest(
            clientId = validUserId()
        )
    }

    protected fun validAddItemToCartRequestJson(): String {
        return objectMapper.writeValueAsString(
            es.urjc.realfood.clients.infrastructure.api.rest.AddItemToCartRequest(
                itemId = validItemId(),
                quantity = 5
            )
        )
    }

    protected fun validAddItemToCartRequest(): AddItemToCartRequest {
        return AddItemToCartRequest(
            clientId = validUserId(),
            itemId = validItemId(),
            quantity = 5
        )
    }

    protected fun validAddItemToCartResponse(): AddItemToCartResponse {
        return AddItemToCartResponse(
            itemId = validItemId(),
            quantity = 5
        )
    }

    protected fun validDeleteItemFromCartRequestJson(): String {
        return objectMapper.writeValueAsString(
            es.urjc.realfood.clients.infrastructure.api.rest.DeleteItemFromCartRequest(
                itemId = validItemId()
            )
        )
    }

    protected fun validDeleteItemFromCartRequest(): DeleteItemFromCartRequest {
        return DeleteItemFromCartRequest(
            clientId = validUserId(),
            itemId = validItemId()
        )
    }

    protected fun validCheckoutCartRequestJson(): String {
        return objectMapper.writeValueAsString(validCheckoutCartRequest())
    }

    protected fun validCheckoutCartRequest(): CheckoutCartRequest {
        return CheckoutCartRequest(
            clientId = validUserId()
        )
    }

    protected fun validCheckoutCartResponse(): CheckoutCartResponse {
        return CheckoutCartResponse(
            orderId = validOrderId()
        )
    }

    protected fun validCartId(): String = "89a135b8-98dc-4e57-a22f-b5f99c6b1a99"

    protected fun validItemId(): String = "89a135b8-98dc-4e57-a22f-b5f99c6b1a00"

    protected fun validUserId(): String = "89a135b8-98dc-4e57-a22f-b5f99c6b1a11"

    protected fun validOrderId(): String = "89a135b8-98dc-4e57-a22f-b5f99c6b1a22"

    protected fun validJwt(): String = jwtGeneratorService.generateJwt(validUserId())

}