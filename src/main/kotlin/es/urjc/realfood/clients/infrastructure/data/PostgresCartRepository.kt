package es.urjc.realfood.clients.infrastructure.data

import es.urjc.realfood.clients.domain.Cart
import es.urjc.realfood.clients.domain.CartId
import es.urjc.realfood.clients.domain.ClientId
import es.urjc.realfood.clients.domain.repository.CartRepository
import org.springframework.stereotype.Component

@Component
class PostgresCartRepository(private val jpaRepository: CartJpaRepository) : CartRepository {

    override fun findById(id: CartId): Cart = jpaRepository.findById(id).orElse(null)

    override fun save(cart: Cart): Cart = jpaRepository.save(cart)

    override fun findByClientId(id: ClientId): Cart = jpaRepository.findByClientId(id).orElse(null)

    override fun deleteByClientId(id: ClientId) = jpaRepository.deleteByClient_Id(id)

}