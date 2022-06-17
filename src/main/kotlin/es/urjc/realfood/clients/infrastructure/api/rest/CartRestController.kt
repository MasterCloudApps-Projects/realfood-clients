package es.urjc.realfood.clients.infrastructure.api.rest

import es.urjc.realfood.clients.application.*
import es.urjc.realfood.clients.application.AddItemToCartRequest
import es.urjc.realfood.clients.application.DeleteItemFromCartRequest
import es.urjc.realfood.clients.infrastructure.api.security.JWTValidatorService
import org.springframework.web.bind.annotation.RestController

@RestController
class CartRestController(
    private val findByClientIdCart: FindByClientIdCart,
    private val addItemToCart: AddItemToCart,
    private val clearCart: ClearCart,
    private val deleteItemFromCart: DeleteItemFromCart,
    private val checkoutCart: CheckoutCart,
    private val jwtValidatorService: JWTValidatorService
) : CartRestApi {

    override fun getCart(headers: Map<String, String>): FindByClientIdCartResponse {
        val subject = jwtValidatorService.getSubjectFromHeaders(headers)
        return findByClientIdCart(FindByClientIdCartRequest(subject))
    }

    override fun clearCart(headers: Map<String, String>) {
        val subject = jwtValidatorService.getSubjectFromHeaders(headers)
        clearCart(ClearCartRequest(subject))
    }

    override fun addItemToCart(
        headers: Map<String, String>,
        addItemToCartRequest: es.urjc.realfood.clients.infrastructure.api.rest.AddItemToCartRequest
    ): AddItemToCartResponse {
        val subject = jwtValidatorService.getSubjectFromHeaders(headers)
        return addItemToCart(
            AddItemToCartRequest(
                clientId = subject,
                itemId = addItemToCartRequest.itemId,
                restaurantId = addItemToCartRequest.restaurantId,
                quantity = addItemToCartRequest.quantity
            )
        )
    }

    override fun deleteItemFromCart(
        headers: Map<String, String>,
        deleteItemFromCartRequest: es.urjc.realfood.clients.infrastructure.api.rest.DeleteItemFromCartRequest
    ) {
        val subject = jwtValidatorService.getSubjectFromHeaders(headers)
        deleteItemFromCart(
            DeleteItemFromCartRequest(
                clientId = subject,
                itemId = deleteItemFromCartRequest.itemId
            )
        )
    }

    override fun checkoutCart(headers: Map<String, String>): CheckoutCartResponse {
        val subject = jwtValidatorService.getSubjectFromHeaders(headers)
        val token = jwtValidatorService.getJwtFromHeaders(headers)
        return checkoutCart(CheckoutCartRequest(subject, token))
    }

}
