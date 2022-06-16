package es.urjc.realfood.clients.infrastructure.external.messaging

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import es.urjc.realfood.clients.application.UpdateOrderStatus
import es.urjc.realfood.clients.application.UpdateOrderStatusRequest
import es.urjc.realfood.clients.domain.Status
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class ShipmentEventRabbitConsumer(
    private val updateOrderStatus: UpdateOrderStatus
) {

    private val logger = LoggerFactory.getLogger(ShipmentEventRabbitConsumer::class.java)

    private val objectMapper = ObjectMapper()
        .registerKotlinModule()
    @RabbitListener(queues = ["sent-orders"], ackMode = "AUTO")
    private fun consume(message: String) {
        val sentEvent = objectMapper.readValue(message, SentEvent::class.java)
        updateOrderStatus(
            UpdateOrderStatusRequest(
                clientId = sentEvent.clientId,
                orderId = sentEvent.orderId,
                status = Status.IN_DELIVERY
            )
        )

        logger.info(
            "[Consumed] sent order '{}' to client '{}'",
            sentEvent.orderId,
            sentEvent.clientId
        )
    }

}

data class SentEvent(
    val clientId: String,
    val orderId: String
)