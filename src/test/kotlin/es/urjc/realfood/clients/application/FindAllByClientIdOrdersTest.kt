package es.urjc.realfood.clients.application

import es.urjc.realfood.clients.domain.ClientObjectProvider.Companion.validClientId
import es.urjc.realfood.clients.domain.Order
import es.urjc.realfood.clients.domain.OrderObjectProvider.Companion.validOrder


abstract class FindAllByClientIdOrdersTest {

    protected fun validFindAllByClientIdOrdersRequest(): FindAllByClientIdOrdersRequest {
        return FindAllByClientIdOrdersRequest(
            clientId = validClientId().toString()
        )
    }

    protected fun invalidFindAllByClientIdOrdersRequest(): FindAllByClientIdOrdersRequest {
        return FindAllByClientIdOrdersRequest(
            clientId = "INVALID"
        )
    }

    protected fun validOrders(): List<Order> {
        return listOf(validOrder())
    }

}