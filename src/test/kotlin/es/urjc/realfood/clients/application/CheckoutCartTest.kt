package es.urjc.realfood.clients.application

import es.urjc.realfood.clients.domain.CartObjectProvider.Companion.validCartItemDto
import es.urjc.realfood.clients.domain.CartObjectProvider.Companion.validRestaurantId
import es.urjc.realfood.clients.domain.ClientObjectProvider.Companion.validClientId
import es.urjc.realfood.clients.domain.ClientObjectProvider.Companion.validJwt
import es.urjc.realfood.clients.domain.OrderObjectProvider.Companion.validOrderId
import es.urjc.realfood.clients.domain.OrderObjectProvider.Companion.validPrice
import es.urjc.realfood.clients.domain.services.CheckoutServiceRequest
import es.urjc.realfood.clients.domain.services.CheckoutServiceResponse

abstract class CheckoutCartTest {

    protected fun validCheckoutCartRequest(): CheckoutCartRequest {
        return CheckoutCartRequest(
            clientId = validClientId().toString(),
            token = validJwt()
        )
    }

    protected fun invalidCheckoutCartRequest(): CheckoutCartRequest {
        return CheckoutCartRequest(
            clientId = "INVALID",
            token = validJwt()
        )
    }

    protected fun validCheckoutServiceRequest(): CheckoutServiceRequest {
        return CheckoutServiceRequest(
            restaurantId = validRestaurantId(),
            lines = listOf(validCartItemDto())
        )
    }

    protected fun validCheckoutServiceResponse(): CheckoutServiceResponse {
        return CheckoutServiceResponse(
            orderId = validOrderId().toString(),
            total = validPrice()
        )
    }

}