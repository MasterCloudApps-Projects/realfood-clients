package es.urjc.realfood.clients.application

import es.urjc.realfood.clients.domain.ClientId
import es.urjc.realfood.clients.domain.OrderId
import es.urjc.realfood.clients.domain.exception.EntityNotFoundException
import es.urjc.realfood.clients.domain.repository.OrderRepository
import es.urjc.realfood.clients.domain.services.ShipmentEvent
import es.urjc.realfood.clients.domain.services.ShipmentEventPublisher
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class SendOrder(
    private val orderRepository: OrderRepository,
    private val shipmentEventPublisher: ShipmentEventPublisher
) {

    operator fun invoke(request: SendOrderRequest) {
        val orderId = OrderId(request.orderId)
        val clientId = ClientId(request.clientId)

        orderRepository.findByIdAndClientId(orderId, clientId)
            ?: throw EntityNotFoundException("Order not found")

        shipmentEventPublisher(
            ShipmentEvent(
                clientId = request.clientId,
                orderId = request.orderId
            )
        )
    }

}

data class SendOrderRequest(
    val clientId: String,
    val orderId: String
)