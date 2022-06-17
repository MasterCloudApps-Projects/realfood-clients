package es.urjc.realfood.clients.application

import es.urjc.realfood.clients.domain.ClientId
import es.urjc.realfood.clients.domain.Order
import es.urjc.realfood.clients.domain.OrderId
import es.urjc.realfood.clients.domain.Status
import es.urjc.realfood.clients.domain.exception.EntityNotFoundException
import es.urjc.realfood.clients.domain.repository.CartRepository
import es.urjc.realfood.clients.domain.repository.OrderRepository
import es.urjc.realfood.clients.domain.services.*
import es.urjc.realfood.clients.domain.services.CartItemDto
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class CheckoutCart(
    private val cartRepository: CartRepository,
    private val orderRepository: OrderRepository,
    private val checkoutCartService: CheckoutCartService,
    private val paymentEventPublisher: PaymentEventPublisher
) {

    private val logger = LoggerFactory.getLogger(CheckoutCart::class.java)

    operator fun invoke(request: CheckoutCartRequest): CheckoutCartResponse {
        val clientId = ClientId(request.clientId)

        val cart = cartRepository.findByClientId(clientId)
            ?: throw EntityNotFoundException("Cart not found")

        if (cart.items.isEmpty())
            throw IllegalArgumentException("Empty cart!")

        if (cart.items.values.map { item -> item.restaurantId }.toSet().size != 1)
            if (cart.items.isEmpty())
                throw IllegalArgumentException("Cannot request multiple restaurants order!")

        val response = checkoutCartService(
            CheckoutServiceRequest(
                restaurantId = cart.items.values.first().restaurantId,
                lines = cart.items.map { item ->
                    CartItemDto(
                        itemId = item.value.itemId,
                        qty = item.value.quantity
                    )
                }
            ), request.token
        )

        logger.info("All products available in new order for client with id: {}", clientId.toString())

        val order = Order(
            id = OrderId(response.orderId),
            status = Status.PENDING,
            client = cart.client,
            price = response.total!!
        )

        orderRepository.save(order)

        logger.info("New order in BD with id '{}' for client with id '{}'", order.id.toString(), clientId.toString())

        paymentEventPublisher(
            PaymentEvent(
                clientId = clientId.toString(),
                orderId = order.id.toString(),
                price = order.price
            )
        )

        cart.items.clear()

        cartRepository.save(cart)

        return CheckoutCartResponse(
            orderId = order.id.toString()
        )
    }

}

data class CheckoutCartRequest(
    val clientId: String,
    val token: String
)

data class CheckoutCartResponse(
    val orderId: String
)