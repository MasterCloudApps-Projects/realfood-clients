package es.urjc.realfood.clients.application

import es.urjc.realfood.clients.domain.ClientId
import es.urjc.realfood.clients.domain.OrderId
import es.urjc.realfood.clients.domain.exception.EntityNotFoundException
import es.urjc.realfood.clients.domain.repository.OrderRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class FindByIdAndClientIdOrder(
    private val orderRepository: OrderRepository
) {

    operator fun invoke(request: FindByIdAndClientIdOrderRequest): FindByIdAndClientIdOrderResponse {
        val clientId = ClientId(request.clientId)
        val orderId = OrderId(request.orderId)

        val order = orderRepository.findByIdAndClientId(orderId, clientId)
            ?: throw EntityNotFoundException("Order not found")

        return FindByIdAndClientIdOrderResponse(
            orderId = order.id.toString(),
            status = order.status.toString()
        )
    }

}

data class FindByIdAndClientIdOrderRequest(
    val clientId: String,
    val orderId: String
)

data class FindByIdAndClientIdOrderResponse(
    val orderId: String,
    val status: String
)