package es.urjc.realfood.clients.infrastructure.external.messaging

import com.fasterxml.jackson.databind.ObjectMapper
import es.urjc.realfood.clients.domain.services.PaymentEvent
import es.urjc.realfood.clients.domain.services.PaymentEventPublisher
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

@Component
class PaymentEventRabbitPublisher(
    private val rabbitTemplate: RabbitTemplate
) : PaymentEventPublisher {

    private val logger = LoggerFactory.getLogger(PaymentEventRabbitPublisher::class.java)

    private val objectMapper: ObjectMapper = ObjectMapper()

    private val queueName: String = "checkout-cart"

    override fun invoke(paymentEvent: PaymentEvent) {
        val msg: String = objectMapper.writeValueAsString(paymentEvent)
        rabbitTemplate.convertAndSend(queueName, msg)
        logger.info("[Published] payment request sent from client: {}", paymentEvent.clientId)
    }

}