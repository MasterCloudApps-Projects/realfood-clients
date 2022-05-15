package es.urjc.realfood.clients.integration

import es.urjc.realfood.clients.application.*
import es.urjc.realfood.clients.domain.CartObjectProvider.Companion.validCartIdString
import es.urjc.realfood.clients.domain.CartObjectProvider.Companion.validItemId
import es.urjc.realfood.clients.domain.ClientObjectProvider.Companion.validClientIdString
import es.urjc.realfood.clients.domain.ClientObjectProvider.Companion.validJwt
import es.urjc.realfood.clients.domain.OrderObjectProvider.Companion.validOrderIdString
import es.urjc.realfood.clients.domain.exception.EntityNotFoundException
import es.urjc.realfood.clients.infrastructure.api.rest.CartRestController
import es.urjc.realfood.clients.infrastructure.api.rest.CartRestControllerTest
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
class CartRestControllerIntegrationTest : CartRestControllerTest() {

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

    @Test
    fun `given cart endpoint when get user cart then return status ok`() {
        `when`(jwtValidatorService.getSubjectFromHeaders(anyMap()))
            .thenReturn(validClientIdString())

        `when`(findByClientIdCart(validFindByClientIdCartRequest()))
            .thenReturn(validFindByClientIdCartResponse())

        RestAssured.given()
            .request()
            .header("Authorization", "Bearer ${validJwt()}")
            .`when`()
            .get("/api/clients/me/cart")
            .then()
            .assertThat()
            .statusCode(200)
            .body("cartId", Matchers.equalTo(validCartIdString()))
            .extract().`as`(FindByClientIdCartResponse::class.java)
    }

    @Test
    fun `given cart endpoint when get user cart of nonexistent user then return 404 status code`() {
        `when`(jwtValidatorService.getSubjectFromHeaders(anyMap()))
            .thenReturn(validClientIdString())

        `when`(findByClientIdCart(validFindByClientIdCartRequest()))
            .thenThrow(EntityNotFoundException("NOT FOUND"))

        RestAssured.given()
            .request()
            .header("Authorization", "Bearer ${validJwt()}")
            .`when`()
            .get("/api/clients/me/cart")
            .then()
            .assertThat()
            .statusCode(404)
            .body("reason", Matchers.equalTo("NOT FOUND"))
    }

    @Test
    fun `given cart endpoint when illegal user id then return 400 status code`() {
        `when`(jwtValidatorService.getSubjectFromHeaders(anyMap()))
            .thenReturn(validClientIdString())

        `when`(findByClientIdCart(validFindByClientIdCartRequest()))
            .thenThrow(IllegalArgumentException("ILLEGAL"))

        RestAssured.given()
            .request()
            .header("Authorization", "Bearer ${validJwt()}")
            .`when`()
            .get("/api/clients/me/cart")
            .then()
            .assertThat()
            .statusCode(400)
            .body("reason", Matchers.equalTo("ILLEGAL"))
    }

    @Test
    fun `given cart endpoint when clear user cart then return status ok`() {
        `when`(jwtValidatorService.getSubjectFromHeaders(anyMap()))
            .thenReturn(validClientIdString())

        RestAssured.given()
            .request()
            .header("Authorization", "Bearer ${validJwt()}")
            .`when`()
            .delete("/api/clients/me/cart")
            .then()
            .assertThat()
            .statusCode(200)
    }

    @Test
    fun `given cart endpoint when clear user cart of nonexistent user then return 404 status code`() {
        `when`(jwtValidatorService.getSubjectFromHeaders(anyMap()))
            .thenReturn(validClientIdString())

        `when`(clearCart(validClearCartRequest()))
            .thenThrow(EntityNotFoundException("NOT FOUND"))

        RestAssured.given()
            .request()
            .header("Authorization", "Bearer ${validJwt()}")
            .`when`()
            .delete("/api/clients/me/cart")
            .then()
            .assertThat()
            .statusCode(404)
            .body("reason", Matchers.equalTo("NOT FOUND"))
    }

    @Test
    fun `given add item to cart endpoint when add item then return status ok`() {
        `when`(jwtValidatorService.getSubjectFromHeaders(anyMap()))
            .thenReturn(validClientIdString())

        `when`(addItemToCart(validAddItemToCartRequest()))
            .thenReturn(validAddItemToCartResponse())

        RestAssured.given()
            .request()
            .header("Authorization", "Bearer ${validJwt()}")
            .body(validAddItemToCartRequestJson())
            .contentType(ContentType.JSON)
            .`when`()
            .post("/api/clients/me/cart/item")
            .then()
            .assertThat()
            .statusCode(200)
            .body("itemId", Matchers.equalTo(validItemId()))
            .extract().`as`(AddItemToCartResponse::class.java)
    }

    @Test
    fun `given add item to cart endpoint when nonexistent user then return 404 status code`() {
        `when`(jwtValidatorService.getSubjectFromHeaders(anyMap()))
            .thenReturn(validClientIdString())

        `when`(addItemToCart(validAddItemToCartRequest()))
            .thenThrow(EntityNotFoundException("NOT FOUND"))

        RestAssured.given()
            .request()
            .header("Authorization", "Bearer ${validJwt()}")
            .body(validAddItemToCartRequestJson())
            .contentType(ContentType.JSON)
            .`when`()
            .post("/api/clients/me/cart/item")
            .then()
            .assertThat()
            .statusCode(404)
            .body("reason", Matchers.equalTo("NOT FOUND"))
    }

    @Test
    fun `given remove item from cart endpoint when remove item then return status ok`() {
        `when`(jwtValidatorService.getSubjectFromHeaders(anyMap()))
            .thenReturn(validClientIdString())

        RestAssured.given()
            .request()
            .header("Authorization", "Bearer ${validJwt()}")
            .body(validDeleteItemFromCartRequestJson())
            .contentType(ContentType.JSON)
            .`when`()
            .delete("/api/clients/me/cart/item")
            .then()
            .assertThat()
            .statusCode(200)
    }

    @Test
    fun `given remove item from cart endpoint when nonexistent user then return 404 status code`() {
        `when`(jwtValidatorService.getSubjectFromHeaders(anyMap()))
            .thenReturn(validClientIdString())

        `when`(deleteItemFromCart(validDeleteItemFromCartRequest()))
            .thenThrow(EntityNotFoundException("NOT FOUND"))

        RestAssured.given()
            .request()
            .header("Authorization", "Bearer ${validJwt()}")
            .body(validDeleteItemFromCartRequestJson())
            .contentType(ContentType.JSON)
            .`when`()
            .delete("/api/clients/me/cart/item")
            .then()
            .assertThat()
            .statusCode(404)
            .body("reason", Matchers.equalTo("NOT FOUND"))
    }

    @Test
    fun `given checkout cart endpoint when checkout then return status ok`() {
        `when`(jwtValidatorService.getSubjectFromHeaders(anyMap()))
            .thenReturn(validClientIdString())

        `when`(checkoutCart(validCheckoutCartRequest()))
            .thenReturn(validCheckoutCartResponse())

        RestAssured.given()
            .request()
            .header("Authorization", "Bearer ${validJwt()}")
            .body(validCheckoutCartRequestJson())
            .contentType(ContentType.JSON)
            .`when`()
            .post("/api/clients/me/cart/checkout")
            .then()
            .assertThat()
            .statusCode(200)
            .body("orderId", Matchers.equalTo(validOrderIdString()))
            .extract().`as`(CheckoutCartResponse::class.java)
    }

    @Test
    fun `given checkout cart endpoint when nonexistent user then return 404 status code`() {
        `when`(jwtValidatorService.getSubjectFromHeaders(anyMap()))
            .thenReturn(validClientIdString())

        `when`(checkoutCart(validCheckoutCartRequest()))
            .thenThrow(EntityNotFoundException("NOT FOUND"))

        RestAssured.given()
            .request()
            .header("Authorization", "Bearer ${validJwt()}")
            .body(validCheckoutCartRequestJson())
            .contentType(ContentType.JSON)
            .`when`()
            .post("/api/clients/me/cart/checkout")
            .then()
            .assertThat()
            .statusCode(404)
            .body("reason", Matchers.equalTo("NOT FOUND"))
    }

}