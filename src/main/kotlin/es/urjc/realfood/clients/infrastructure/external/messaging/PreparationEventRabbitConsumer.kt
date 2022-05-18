package es.urjc.realfood.clients.infrastructure.external.messaging

import com.fasterxml.jackson.databind.ObjectMapper
import es.urjc.realfood.clients.application.PrepareOrder
import es.urjc.realfood.clients.application.PrepareOrderRequest
import es.urjc.realfood.clients.application.UpdateOrderStatus
import es.urjc.realfood.clients.application.UpdateOrderStatusRequest
import es.urjc.realfood.clients.domain.Status
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class PreparationEventRabbitConsumer(
    private val updateOrderStatus: UpdateOrderStatus,
    private val prepareOrder: PrepareOrder
) {

    private val logger = LoggerFactory.getLogger(PaymentEventRabbitConsumer::class.java)

    private val objectMapper = ObjectMapper()

    @RabbitListener(queues = ["prepared-orders"], ackMode = "AUTO")
    private fun consume(message: String) {
        val preparedEvent = objectMapper.readValue(message, PreparedEvent::class.java)
        updateOrderStatus(
            UpdateOrderStatusRequest(
                clientId = preparedEvent.clientId,
                orderId = preparedEvent.orderId,
                status = Status.PREPARED
            )
        )

        logger.info("[Consumed] prepared order '{}'", preparedEvent.orderId)

        prepareOrder(
            PrepareOrderRequest(
                clientId = preparedEvent.clientId,
                orderId = preparedEvent.orderId
            )
        )
    }

}

data class PreparedEvent(
    val clientId: String,
    val orderId: String
)