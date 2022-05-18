package es.urjc.realfood.clients.application

import es.urjc.realfood.clients.domain.ClientObjectProvider.Companion.validClientIdString
import es.urjc.realfood.clients.domain.OrderObjectProvider.Companion.validOrderIdString

abstract class SendOrderTest {

    protected fun validSendOrderRequest(): SendOrderRequest {
        return SendOrderRequest(
            clientId = validClientIdString(),
            orderId = validOrderIdString()
        )
    }

    protected fun invalidSendOrderRequest(): SendOrderRequest {
        return SendOrderRequest(
            clientId = "INVALID",
            orderId = "INVALID"
        )
    }

}