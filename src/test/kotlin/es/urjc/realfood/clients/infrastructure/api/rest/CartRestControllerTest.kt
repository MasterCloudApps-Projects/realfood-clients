package es.urjc.realfood.clients.infrastructure.api.rest

import com.fasterxml.jackson.databind.ObjectMapper
import es.urjc.realfood.clients.application.*
import es.urjc.realfood.clients.application.AddItemToCartRequest
import es.urjc.realfood.clients.application.DeleteItemFromCartRequest
import es.urjc.realfood.clients.domain.CartObjectProvider.Companion.validCartId
import es.urjc.realfood.clients.domain.CartObjectProvider.Companion.validItemId
import es.urjc.realfood.clients.domain.CartObjectProvider.Companion.validQuantity
import es.urjc.realfood.clients.domain.ClientObjectProvider.Companion.validClientIdString
import es.urjc.realfood.clients.domain.OrderObjectProvider.Companion.validOrderId


abstract class CartRestControllerTest {

    private val objectMapper = ObjectMapper()

    protected fun validFindByClientIdCartRequest(): FindByClientIdCartRequest {
        return FindByClientIdCartRequest(
            clientId = validClientIdString()
        )
    }

    protected fun validFindByClientIdCartResponse(): FindByClientIdCartResponse {
        return FindByClientIdCartResponse(
            cartId = validCartId().toString(),
            items = emptyList()
        )
    }

    protected fun validClearCartRequest(): ClearCartRequest {
        return ClearCartRequest(
            clientId = validClientIdString()
        )
    }

    protected fun validAddItemToCartRequestJson(): String {
        return objectMapper.writeValueAsString(
            AddItemToCartRequest(
                itemId = validItemId(),
                quantity = validQuantity()
            )
        )
    }

    protected fun validAddItemToCartRequest(): AddItemToCartRequest {
        return AddItemToCartRequest(
            clientId = validClientIdString(),
            itemId = validItemId(),
            quantity = validQuantity()
        )
    }

    protected fun validAddItemToCartResponse(): AddItemToCartResponse {
        return AddItemToCartResponse(
            itemId = validItemId(),
            quantity = validQuantity()
        )
    }

    protected fun validDeleteItemFromCartRequestJson(): String {
        return objectMapper.writeValueAsString(
            DeleteItemFromCartRequest(
                itemId = validItemId()
            )
        )
    }

    protected fun validDeleteItemFromCartRequest(): DeleteItemFromCartRequest {
        return DeleteItemFromCartRequest(
            clientId = validClientIdString(),
            itemId = validItemId()
        )
    }

    protected fun validCheckoutCartRequestJson(): String {
        return objectMapper.writeValueAsString(validCheckoutCartRequest())
    }

    protected fun validCheckoutCartRequest(): CheckoutCartRequest {
        return CheckoutCartRequest(
            clientId = validClientIdString()
        )
    }

    protected fun validCheckoutCartResponse(): CheckoutCartResponse {
        return CheckoutCartResponse(
            orderId = validOrderId().toString()
        )
    }

}