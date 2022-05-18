package es.urjc.realfood.clients.application

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

        orderRepository.findById(orderId)
            ?: throw EntityNotFoundException("Order not found")

        preparationEventPublisher(
            PreparationEvent(
                orderId = request.orderId
            )
        )
    }

}

data class PrepareOrderRequest(
    val orderId: String
)