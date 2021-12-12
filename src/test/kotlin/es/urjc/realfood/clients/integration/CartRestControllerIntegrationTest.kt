package es.urjc.realfood.clients.integration

import es.urjc.realfood.clients.application.FindByClientIdCartResponse
import es.urjc.realfood.clients.domain.exception.EntityNotFoundException
import es.urjc.realfood.clients.infrastructure.api.rest.CartRestControllerTest
import io.restassured.RestAssured
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyMap

class CartRestControllerIntegrationTest : CartRestControllerTest() {

    @Test
    fun `given cart endpoint when get user cart then return status ok`() {
        `when`(jwtValidatorService.getSubjectFromHeaders(anyMap()))
            .thenReturn(validUserId())
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
            .body("cartId", Matchers.equalTo(validCartId()))
            .extract().`as`(FindByClientIdCartResponse::class.java)
    }

    @Test
    fun `given cart endpoint when get user cart of nonexistent user then return 404 status code`() {
        `when`(jwtValidatorService.getSubjectFromHeaders(anyMap()))
            .thenReturn(validUserId())
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
            .thenReturn(validUserId())
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
            .thenReturn(validUserId())

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
            .thenReturn(validUserId())
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

}