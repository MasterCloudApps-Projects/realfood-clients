package es.urjc.realfood.clients.domain.repository

import es.urjc.realfood.clients.domain.Cart
import es.urjc.realfood.clients.domain.CartId

interface CartRepository {

    fun findById(id: CartId): Cart

    fun save(cart: Cart): Cart

}