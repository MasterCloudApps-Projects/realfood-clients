package es.urjc.realfood.clients.application

import es.urjc.realfood.clients.domain.ClientObjectProvider.Companion.validClientIdString
import es.urjc.realfood.clients.domain.OrderObjectProvider.Companion.validOrderIdString

abstract class PrepareOrderTest {

    protected fun validPrepareOrderRequest(): PrepareOrderRequest {
        return PrepareOrderRequest(
            clientId = validClientIdString(),
            orderId = validOrderIdString()
        )
    }

    protected fun invalidPrepareOrderRequest(): PrepareOrderRequest {
        return PrepareOrderRequest(
            clientId = "INVALID",
            orderId = "INVALID"
        )
    }

}