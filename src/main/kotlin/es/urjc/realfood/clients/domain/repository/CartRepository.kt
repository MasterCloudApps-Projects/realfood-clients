package es.urjc.realfood.clients.domain.repository

import es.urjc.realfood.clients.domain.Cart
import es.urjc.realfood.clients.domain.CartId
import es.urjc.realfood.clients.domain.ClientId

interface CartRepository {

    fun findById(id: CartId): Cart?

    fun save(cart: Cart): Cart

    fun findByClientId(id: ClientId): Cart?

    fun deleteByClientId(id: ClientId)

}