package es.urjc.realfood.clients.application

import es.urjc.realfood.clients.domain.CartItem
import es.urjc.realfood.clients.domain.ClientId
import es.urjc.realfood.clients.domain.exception.EntityNotFoundException
import es.urjc.realfood.clients.domain.exception.ProductException
import es.urjc.realfood.clients.domain.repository.CartRepository
import es.urjc.realfood.clients.domain.services.FindByIdProductService
import es.urjc.realfood.clients.domain.services.FindByIdProductServiceRequest
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class AddItemToCart(
    private val cartRepository: CartRepository,
    private val findByIdProductService: FindByIdProductService
) {

    operator fun invoke(request: AddItemToCartRequest): AddItemToCartResponse {
        val clientId = ClientId(request.clientId)

        if (request.quantity <= 0)
            throw IllegalArgumentException("Quantity must be a integer positive value")

        val cart = cartRepository.findByClientId(clientId)
            ?: throw EntityNotFoundException("Cart not found")

        val response = findByIdProductService(
            FindByIdProductServiceRequest(
                request.itemId
            )
        )

        if (response.status == 500)
            throw ProductException("Error from Restaurants API")

        if (response.status != 200)
            throw EntityNotFoundException("Product not found")

        val cartItem = cart.items[request.itemId]
        if (cartItem == null) {
            cart.items[request.itemId] = CartItem(request.itemId, request.quantity)
        } else {
            cart.items[request.itemId] = CartItem(cartItem.itemId, cartItem.quantity + request.quantity)
        }

        cartRepository.save(cart)

        return AddItemToCartResponse(
            itemId = cart.items[request.itemId]!!.itemId,
            quantity = cart.items[request.itemId]!!.quantity
        )
    }

}

data class AddItemToCartRequest(
    val clientId: String,
    val itemId: String,
    val quantity: Int
)

data class AddItemToCartResponse(
    val itemId: String,
    val quantity: Int
)