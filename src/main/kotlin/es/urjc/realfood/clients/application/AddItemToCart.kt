package es.urjc.realfood.clients.application

import es.urjc.realfood.clients.domain.CartItem
import es.urjc.realfood.clients.domain.ClientId
import es.urjc.realfood.clients.domain.exception.EntityNotFoundException
import es.urjc.realfood.clients.domain.repository.CartRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class AddItemToCart(
    private val cartRepository: CartRepository
) {

    operator fun invoke(request: AddItemToCartRequest): AddItemToCartResponse {
        val clientId = ClientId(request.clientId)

        if (request.quantity <= 0)
            throw IllegalArgumentException("Quantity must be a integer positive value")

        val cart = cartRepository.findByClientId(clientId)
            ?: throw EntityNotFoundException("Cart not found")

        val cartItem = cart.items[request.itemId]
        if (cartItem == null) {
            cart.items[request.itemId] =
                CartItem(request.itemId, request.restaurantId, request.quantity)
        } else {
            cart.items[request.itemId] =
                CartItem(cartItem.itemId, request.restaurantId, cartItem.quantity + request.quantity)
        }

        cartRepository.save(cart)

        return AddItemToCartResponse(
            itemId = cart.items[request.itemId]!!.itemId,
            restaurantId = cart.items[request.itemId]!!.restaurantId,
            quantity = cart.items[request.itemId]!!.quantity
        )
    }

}

data class AddItemToCartRequest(
    val clientId: String,
    val itemId: String,
    val restaurantId: String,
    val quantity: Int
)

data class AddItemToCartResponse(
    val itemId: String,
    val restaurantId: String,
    val quantity: Int
)