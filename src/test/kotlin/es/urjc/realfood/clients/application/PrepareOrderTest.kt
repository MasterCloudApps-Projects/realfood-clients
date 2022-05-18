package es.urjc.realfood.clients.application

import es.urjc.realfood.clients.domain.OrderObjectProvider.Companion.validOrderIdString

abstract class PrepareOrderTest {

    protected fun validPrepareOrderRequest(): PrepareOrderRequest {
        return PrepareOrderRequest(
            orderId = validOrderIdString()
        )
    }

    protected fun invalidPrepareOrderRequest(): PrepareOrderRequest {
        return PrepareOrderRequest(
            orderId = "INVALID"
        )
    }

}