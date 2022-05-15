package es.urjc.realfood.clients.application

import es.urjc.realfood.clients.domain.ClientObjectProvider.Companion.validClientId
import es.urjc.realfood.clients.domain.OrderObjectProvider.Companion.validOrderId

abstract class FindByIdAndClientIdOrderTest {

    protected fun validRequest(): FindByIdAndClientIdOrderRequest {
        return FindByIdAndClientIdOrderRequest(
            clientId = validClientId().toString(),
            orderId = validOrderId().toString()
        )
    }

    protected fun invalidOrderIdRequest(): FindByIdAndClientIdOrderRequest {
        return FindByIdAndClientIdOrderRequest(
            clientId = "INVALID",
            orderId = validOrderId().toString()
        )
    }

    protected fun invalidClientIdRequest(): FindByIdAndClientIdOrderRequest {
        return FindByIdAndClientIdOrderRequest(
            clientId = validClientId().toString(),
            orderId = "INVALID"
        )
    }

}