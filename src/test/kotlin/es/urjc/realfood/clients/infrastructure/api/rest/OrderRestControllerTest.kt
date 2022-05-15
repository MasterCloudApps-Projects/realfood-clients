package es.urjc.realfood.clients.infrastructure.api.rest

import es.urjc.realfood.clients.application.FindAllByClientIdOrdersRequest
import es.urjc.realfood.clients.application.FindAllByClientIdOrdersResponse
import es.urjc.realfood.clients.application.FindByIdAndClientIdOrderRequest
import es.urjc.realfood.clients.application.FindByIdAndClientIdOrderResponse
import es.urjc.realfood.clients.domain.ClientObjectProvider.Companion.validClientIdString
import es.urjc.realfood.clients.domain.OrderObjectProvider.Companion.validOrderIdString

abstract class OrderRestControllerTest {

    protected fun validFindAllRequest(): FindAllByClientIdOrdersRequest {
        return FindAllByClientIdOrdersRequest(
            clientId = validClientIdString()
        )
    }

    protected fun validFindAllResponse(): FindAllByClientIdOrdersResponse {
        return FindAllByClientIdOrdersResponse(
            clientId = validClientIdString(),
            orders = emptyList()
        )
    }

    protected fun validFindRequest(): FindByIdAndClientIdOrderRequest {
        return FindByIdAndClientIdOrderRequest(
            clientId = validClientIdString(),
            orderId = validOrderIdString()
        )
    }

    protected fun validFindResponse(): FindByIdAndClientIdOrderResponse {
        return FindByIdAndClientIdOrderResponse(
            orderId = validOrderIdString(),
            status = "PENDING"
        )
    }

}