package es.urjc.realfood.clients.infrastructure.data

import es.urjc.realfood.clients.domain.Cart
import es.urjc.realfood.clients.domain.CartId
import es.urjc.realfood.clients.domain.ClientId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CartJpaRepository : JpaRepository<Cart, CartId> {

    fun findByClientId(id: ClientId): Optional<Cart>

    fun deleteByClient_Id(id: ClientId)

}