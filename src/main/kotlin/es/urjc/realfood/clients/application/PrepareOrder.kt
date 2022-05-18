package es.urjc.realfood.clients.application

import es.urjc.realfood.clients.domain.ClientId
import es.urjc.realfood.clients.domain.OrderId
import es.urjc.realfood.clients.domain.exception.EntityNotFoundException
import es.urjc.realfood.clients.domain.repository.OrderRepository
import es.urjc.realfood.clients.domain.services.PreparationEvent
import es.urjc.realfood.clients.domain.services.PreparationEventPublisher
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class PrepareOrder(
    private val orderRepository: OrderRepository,
    private val preparationEventPublisher: PreparationEventPublisher
) {

    operator fun invoke(request: PrepareOrderRequest) {
        val orderId = OrderId(request.orderId)
        val clientId = ClientId(request.clientId)

        orderRepository.findByIdAndClientId(orderId, clientId)
            ?: throw EntityNotFoundException("Order not found")

        preparationEventPublisher(
            PreparationEvent(
                clientId = request.clientId,
                orderId = request.orderId
            )
        )
    }

}

data class PrepareOrderRequest(
    val clientId: String,
    val orderId: String
)