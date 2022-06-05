package es.urjc.realfood.clients.application

import es.urjc.realfood.clients.domain.ClientId
import es.urjc.realfood.clients.domain.OrderId
import es.urjc.realfood.clients.domain.Status
import es.urjc.realfood.clients.domain.exception.EntityNotFoundException
import es.urjc.realfood.clients.domain.repository.OrderRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class UpdateOrderStatus(
    private val orderRepository: OrderRepository
) {

    operator fun invoke(request: UpdateOrderStatusRequest): UpdateOrderStatusResponse {
        val clientId = ClientId(request.clientId)
        val orderId = OrderId(request.orderId)

        val order = orderRepository.findByIdAndClientId(orderId, clientId)
            ?: throw EntityNotFoundException("Order not found")

        order.status = request.status

        orderRepository.save(order)

        return UpdateOrderStatusResponse(
            orderId = request.orderId,
            status = order.status.toString()
        )
    }

}

data class UpdateOrderStatusRequest(
    val clientId: String,
    val orderId: String,
    val status: Status
)

data class UpdateOrderStatusResponse(
    val orderId: String,
    val status: String
)