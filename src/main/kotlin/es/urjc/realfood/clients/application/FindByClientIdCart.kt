package es.urjc.realfood.clients.application

import es.urjc.realfood.clients.domain.ClientId
import es.urjc.realfood.clients.domain.exception.EntityNotFoundException
import es.urjc.realfood.clients.domain.repository.CartRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class FindByClientIdCart(
    private val cartRepository: CartRepository,
) {

    operator fun invoke(request: FindByClientIdCartRequest): FindByClientIdCartResponse {
        val clientId = ClientId(request.clientId)

        val cart = cartRepository.findByClientId(clientId)
            ?: throw EntityNotFoundException("Cart not found")

        return FindByClientIdCartResponse(
            cartId = cart.id.toString(),
            items = cart.items.map { item ->
                CartItemDto(item.value.itemId, item.value.quantity)
            }
        )
    }

}

data class FindByClientIdCartRequest(
    val clientId: String
)

data class FindByClientIdCartResponse(
    val cartId: String,
    val items: List<CartItemDto>
)

data class CartItemDto(
    val itemId: String,
    val quantity: Int
)