package es.urjc.realfood.clients.application

import es.urjc.realfood.clients.domain.ClientId
import es.urjc.realfood.clients.domain.Order
import es.urjc.realfood.clients.domain.OrderId
import es.urjc.realfood.clients.domain.Status
import es.urjc.realfood.clients.domain.exception.EntityNotFoundException
import es.urjc.realfood.clients.domain.exception.ProductException
import es.urjc.realfood.clients.domain.repository.CartRepository
import es.urjc.realfood.clients.domain.repository.OrderRepository
import es.urjc.realfood.clients.domain.services.CheckoutCartService
import es.urjc.realfood.clients.domain.services.CheckoutServiceRequest
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class CheckoutCart(
    private val cartRepository: CartRepository,
    private val orderRepository: OrderRepository,
    private val checkoutCartService: CheckoutCartService
) {

    operator fun invoke(request: CheckoutCartRequest): CheckoutCartResponse {
        val clientId = ClientId(request.clientId)

        val cart = cartRepository.findByClientId(clientId)
            ?: throw EntityNotFoundException("Cart not found")

        val response = checkoutCartService(
            CheckoutServiceRequest(
                clientId = clientId.toString(),
                cart.items.map { item -> item.value.itemId }
            )
        )

        if (response.statusCode != 200)
            throw ProductException("Error from Restaurants API")

        val order = Order(
            id = OrderId(response.orderId!!),
            status = Status.PENDING,
            client = cart.client
        )

        orderRepository.save(order)

        return CheckoutCartResponse(
            orderId = order.id.toString()
        )
    }

}

data class CheckoutCartRequest(
    val clientId: String
)

data class CheckoutCartResponse(
    val orderId: String
)