package es.urjc.realfood.clients.application

import es.urjc.realfood.clients.domain.ClientObjectProvider.Companion.validClientIdString
import es.urjc.realfood.clients.domain.OrderObjectProvider.Companion.inProgressStatus
import es.urjc.realfood.clients.domain.OrderObjectProvider.Companion.validOrderIdString

abstract class UpdateOrderStatusTest {

    protected fun validUpdateOrderStatusRequest(): UpdateOrderStatusRequest {
        return UpdateOrderStatusRequest(
            clientId = validClientIdString(),
            orderId = validOrderIdString(),
            status = inProgressStatus()
        )
    }

}