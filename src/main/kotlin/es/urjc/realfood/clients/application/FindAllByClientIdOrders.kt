package es.urjc.realfood.clients.application

import es.urjc.realfood.clients.domain.ClientId
import es.urjc.realfood.clients.domain.repository.OrderRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class FindAllByClientIdOrders(
    private val orderRepository: OrderRepository
) {

    operator fun invoke(request: FindAllByClientIdOrdersRequest): FindAllByClientIdOrdersResponse {
        val clientId = ClientId(request.clientId)

        val orders = orderRepository.findAllByClientId(clientId)

        return FindAllByClientIdOrdersResponse(
            clientId = clientId.toString(),
            orders = orders.map { order -> OrderDto(order.id.toString(), order.status.toString()) }
        )
    }

}

data class FindAllByClientIdOrdersRequest(
    val clientId: String
)

data class FindAllByClientIdOrdersResponse(
    val clientId: String,
    val orders: List<OrderDto>
)

data class OrderDto(
    val orderId: String,
    val status: String
)