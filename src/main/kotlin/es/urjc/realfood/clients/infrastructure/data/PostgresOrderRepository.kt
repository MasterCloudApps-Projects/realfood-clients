package es.urjc.realfood.clients.infrastructure.data

import es.urjc.realfood.clients.domain.ClientId
import es.urjc.realfood.clients.domain.Order
import es.urjc.realfood.clients.domain.OrderId
import es.urjc.realfood.clients.domain.repository.OrderRepository
import org.springframework.stereotype.Component

@Component
class PostgresOrderRepository(private val jpaRepository: OrderJpaRepository) : OrderRepository {

    override fun findById(id: OrderId): Order? = jpaRepository.findById(id).orElse(null)

    override fun save(order: Order): Order = jpaRepository.save(order)

    override fun findAllByClientId(clientId: ClientId): List<Order> = jpaRepository.findAllByClientId(clientId)

}