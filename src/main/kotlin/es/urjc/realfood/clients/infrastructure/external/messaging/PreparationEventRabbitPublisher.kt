package es.urjc.realfood.clients.infrastructure.external.messaging

import com.fasterxml.jackson.databind.ObjectMapper
import es.urjc.realfood.clients.domain.services.PreparationEvent
import es.urjc.realfood.clients.domain.services.PreparationEventPublisher
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

@Component
class PreparationEventRabbitPublisher(
    private val rabbitTemplate: RabbitTemplate
) : PreparationEventPublisher {

    private val logger = LoggerFactory.getLogger(PreparationEventRabbitPublisher::class.java)

    private val objectMapper: ObjectMapper = ObjectMapper()

    private val queueName: String = "prepare-order"

    override fun invoke(preparationEvent: PreparationEvent) {
        val msg: String = objectMapper.writeValueAsString(preparationEvent)
        rabbitTemplate.convertAndSend(queueName, msg)
        logger.info("[Published] preparation request sent for order: {}", preparationEvent.orderId)
    }

}