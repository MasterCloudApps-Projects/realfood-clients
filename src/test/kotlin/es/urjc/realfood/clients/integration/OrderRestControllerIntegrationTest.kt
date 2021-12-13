package es.urjc.realfood.clients.integration

import es.urjc.realfood.clients.application.FindAllByClientIdOrdersResponse
import es.urjc.realfood.clients.application.FindByIdAndClientIdOrderResponse
import es.urjc.realfood.clients.domain.exception.EntityNotFoundException
import es.urjc.realfood.clients.infrastructure.api.rest.OrderRestControllerTest
import io.restassured.RestAssured
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyMap

class OrderRestControllerIntegrationTest : OrderRestControllerTest() {

    @Test
    fun `given find all user orders endpoint when get orders then return status ok`() {
        `when`(jwtValidatorService.getSubjectFromHeaders(anyMap()))
            .thenReturn(validUserId())
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
            .body("clientId", Matchers.equalTo(validUserId()))
            .extract().`as`(FindAllByClientIdOrdersResponse::class.java)
    }

    @Test
    fun `given find user order endpoint when get order then return status ok`() {
        `when`(jwtValidatorService.getSubjectFromHeaders(anyMap()))
            .thenReturn(validUserId())
        `when`(findByIdAndClientIdOrder(validFindRequest()))
            .thenReturn(validFindResponse())

        RestAssured.given()
            .request()
            .header("Authorization", "Bearer ${validJwt()}")
            .`when`()
            .get("/api/clients/me/orders/${validOrderId()}")
            .then()
            .assertThat()
            .statusCode(200)
            .body("orderId", Matchers.equalTo(validOrderId()))
            .body("status", Matchers.equalTo("PENDING"))
            .extract().`as`(FindByIdAndClientIdOrderResponse::class.java)
    }

    @Test
    fun `given find user order endpoint when not found user or order order then return 404 status code`() {
        `when`(jwtValidatorService.getSubjectFromHeaders(anyMap()))
            .thenReturn(validUserId())
        `when`(findByIdAndClientIdOrder(validFindRequest()))
            .thenThrow(EntityNotFoundException("Order not found"))

        RestAssured.given()
            .request()
            .header("Authorization", "Bearer ${validJwt()}")
            .`when`()
            .get("/api/clients/me/orders/${validOrderId()}")
            .then()
            .assertThat()
            .statusCode(404)
            .body("reason", Matchers.equalTo("Order not found"))
    }

}