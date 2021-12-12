package es.urjc.realfood.clients.application

import es.urjc.realfood.clients.domain.ClientId
import es.urjc.realfood.clients.domain.exception.EntityNotFoundException
import es.urjc.realfood.clients.domain.repository.CartRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class ClearCart(
    private val cartRepository: CartRepository
) {

    operator fun invoke(request: ClearCartRequest) {
        val clientId = ClientId(request.clientId)

        val cart = cartRepository.findByClientId(clientId)
            ?: throw EntityNotFoundException("Cart not found")

        cart.items.clear()

        cartRepository.save(cart)
    }

}

data class ClearCartRequest(
    val clientId: String
)