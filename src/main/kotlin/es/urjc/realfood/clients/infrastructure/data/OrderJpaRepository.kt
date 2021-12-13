package es.urjc.realfood.clients.infrastructure.data

import es.urjc.realfood.clients.domain.ClientId
import es.urjc.realfood.clients.domain.Order
import es.urjc.realfood.clients.domain.OrderId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface OrderJpaRepository : JpaRepository<Order, OrderId> {

    fun findAllByClientId(id: ClientId): List<Order>

    fun findByIdAndClientId(id: OrderId, clientId: ClientId): Optional<Order>

}