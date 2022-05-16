package es.urjc.realfood.clients.infrastructure.external.messaging

import com.fasterxml.jackson.databind.ObjectMapper
import es.urjc.realfood.clients.application.UpdateOrderStatus
import es.urjc.realfood.clients.application.UpdateOrderStatusRequest
import es.urjc.realfood.clients.domain.Status
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class PaymentEventRabbitConsumer(
    val updateOrderStatus: UpdateOrderStatus
) {

    private val logger = LoggerFactory.getLogger(PaymentEventRabbitConsumer::class.java)

    private val objectMapper = ObjectMapper()

    @RabbitListener(queues = ["payments"], ackMode = "AUTO")
    private fun consume(message: String) {
        val payedEvent = objectMapper.readValue(message, PayedEvent::class.java)
        updateOrderStatus(
            UpdateOrderStatusRequest(
                clientId = payedEvent.clientId,
                orderId = payedEvent.orderId,
                status = if (payedEvent.success) Status.IN_PROGRESS else Status.PAYMENT_ERROR
            )
        )

        logger.info(
            "[Consumed] payment response from client '{}' and order '{}'",
            payedEvent.clientId,
            payedEvent.orderId
        )
    }

}

data class PayedEvent(
    val clientId: String,
    val orderId: String,
    val success: Boolean
)