package es.urjc.realfood.clients.infrastructure.external.messaging

import com.fasterxml.jackson.databind.ObjectMapper
import es.urjc.realfood.clients.application.UpdateOrderStatus
import es.urjc.realfood.clients.application.UpdateOrderStatusRequest
import es.urjc.realfood.clients.domain.Status
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class CompletedEventRabbitConsumer(
    private val updateOrderStatus: UpdateOrderStatus
) {

    private val logger = LoggerFactory.getLogger(CompletedEventRabbitConsumer::class.java)

    private val objectMapper = ObjectMapper()

    @RabbitListener(queues = ["completed-orders"], ackMode = "AUTO")
    private fun consume(message: String) {
        val completedEvent = objectMapper.readValue(message, CompletedEvent::class.java)
        updateOrderStatus(
            UpdateOrderStatusRequest(
                clientId = completedEvent.clientId,
                orderId = completedEvent.orderId,
                status = Status.COMPLETED
            )
        )

        logger.info(
            "[Consumed] completed order '{}' to client '{}'",
            completedEvent.orderId,
            completedEvent.clientId
        )
    }

}

data class CompletedEvent(
    val clientId: String,
    val orderId: String
)