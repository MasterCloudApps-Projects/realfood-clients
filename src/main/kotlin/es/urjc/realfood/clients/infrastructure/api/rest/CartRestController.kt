package es.urjc.realfood.clients.infrastructure.api.rest

import es.urjc.realfood.clients.application.*
import es.urjc.realfood.clients.application.AddItemToCartRequest
import es.urjc.realfood.clients.infrastructure.api.security.JWTValidatorService
import org.springframework.web.bind.annotation.RestController

@RestController
class CartRestController(
    private val findByClientIdCart: FindByClientIdCart,
    private val addItemToCart: AddItemToCart,
    private val jwtValidatorService: JWTValidatorService
) : CartRestApi {

    override fun getCart(headers: Map<String, String>): FindByClientIdCartResponse {
        val subject = jwtValidatorService.getSubjectFromHeaders(headers)
        return findByClientIdCart(FindByClientIdCartRequest(subject))
    }

    override fun clearCart(headers: Map<String, String>) {
        TODO("Not yet implemented")
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
                quantity = addItemToCartRequest.quantity
            )
        )
    }

    override fun deleteItemFromCart(headers: Map<String, String>) {
        TODO("Not yet implemented")
    }

    override fun checkoutCart(headers: Map<String, String>) {
        TODO("Not yet implemented")
    }

}
