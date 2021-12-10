package es.urjc.realfood.clients.infrastructure.data

import es.urjc.realfood.clients.domain.Cart
import es.urjc.realfood.clients.domain.CartId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CartJpaRepository : JpaRepository<Cart, CartId>