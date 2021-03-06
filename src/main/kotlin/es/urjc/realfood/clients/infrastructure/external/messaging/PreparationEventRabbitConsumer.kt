package es.urjc.realfood.clients.infrastructure.external.messaging

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import es.urjc.realfood.clients.application.*
import es.urjc.realfood.clients.domain.Status
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class PreparationEventRabbitConsumer(
    private val updateOrderStatus: UpdateOrderStatus,
    private val sendOrder: SendOrder
) {

    private val logger = LoggerFactory.getLogger(PaymentEventRabbitConsumer::class.java)

    private val objectMapper = ObjectMapper()
        .registerKotlinModule()
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

        sendOrder(
            SendOrderRequest(
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