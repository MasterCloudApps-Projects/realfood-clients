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
class PaymentEventRabbitConsumer(
    private val updateOrderStatus: UpdateOrderStatus,
    private val prepareOrder: PrepareOrder
) {

    private val logger = LoggerFactory.getLogger(PaymentEventRabbitConsumer::class.java)

    private val objectMapper = ObjectMapper()

    @RabbitListener(queues = ["payments"], ackMode = "AUTO")
    private fun consume(message: String) {
        val payedEvent = objectMapper.readValue(message, PayedEvent::class.java)
        val status = if (payedEvent.success) Status.IN_PROGRESS else Status.PAYMENT_ERROR
        updateOrderStatus(
            UpdateOrderStatusRequest(
                clientId = payedEvent.clientId,
                orderId = payedEvent.orderId,
                status = status
            )
        )

        logger.info(
            "[Consumed] payment response from client '{}' and order '{}'",
            payedEvent.clientId,
            payedEvent.orderId
        )

        if (status == Status.IN_PROGRESS) {
            prepareOrder(
                PrepareOrderRequest(
                    clientId = payedEvent.clientId,
                    orderId = payedEvent.orderId
                )
            )
        }
    }

}

data class PayedEvent(
    val clientId: String,
    val orderId: String,
    val success: Boolean
)