package es.urjc.realfood.clients.application

import es.urjc.realfood.clients.domain.CartObjectProvider.Companion.validCartItemDto
import es.urjc.realfood.clients.domain.ClientObjectProvider.Companion.validClientId
import es.urjc.realfood.clients.domain.OrderObjectProvider.Companion.validOrderId
import es.urjc.realfood.clients.domain.services.CheckoutServiceRequest
import es.urjc.realfood.clients.domain.services.CheckoutServiceResponse

abstract class CheckoutCartTest {

    protected fun validCheckoutCartRequest(): CheckoutCartRequest {
        return CheckoutCartRequest(
            clientId = validClientId().toString()
        )
    }

    protected fun invalidCheckoutCartRequest(): CheckoutCartRequest {
        return CheckoutCartRequest(
            clientId = "INVALID"
        )
    }

    protected fun validCheckoutServiceRequest(): CheckoutServiceRequest {
        return CheckoutServiceRequest(
            clientId = validClientId().toString(),
            items = listOf(validCartItemDto())
        )
    }

    protected fun validCheckoutServiceResponse(): CheckoutServiceResponse {
        return CheckoutServiceResponse(
            statusCode = 200,
            orderId = validOrderId().toString()
        )
    }

    protected fun invalidCheckoutServiceResponse(): CheckoutServiceResponse {
        return CheckoutServiceResponse(
            statusCode = 400,
            orderId = null
        )
    }

}