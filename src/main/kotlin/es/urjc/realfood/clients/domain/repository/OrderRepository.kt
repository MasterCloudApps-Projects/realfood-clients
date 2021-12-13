package es.urjc.realfood.clients.domain.repository

import es.urjc.realfood.clients.domain.ClientId
import es.urjc.realfood.clients.domain.Order
import es.urjc.realfood.clients.domain.OrderId

interface OrderRepository {

    fun findById(id: OrderId): Order?

    fun findByIdAndClientId(id: OrderId, clientId: ClientId): Order?

    fun save(order: Order): Order

    fun findAllByClientId(clientId: ClientId): List<Order>

}