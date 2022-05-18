package es.urjc.realfood.clients.infrastructure.external.messaging

import com.fasterxml.jackson.databind.ObjectMapper
import es.urjc.realfood.clients.domain.services.ShipmentEvent
import es.urjc.realfood.clients.domain.services.ShipmentEventPublisher
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

@Component
class ShipmentEventRabbitPublisher(
    private val rabbitTemplate: RabbitTemplate
) : ShipmentEventPublisher {

    private val logger = LoggerFactory.getLogger(ShipmentEventRabbitPublisher::class.java)

    private val objectMapper: ObjectMapper = ObjectMapper()

    private val queueName: String = "send-order"

    override fun invoke(shipmentEvent: ShipmentEvent) {
        val msg: String = objectMapper.writeValueAsString(shipmentEvent)
        rabbitTemplate.convertAndSend(queueName, msg)
        logger.info("[Published] shipment request sent for order: {}", shipmentEvent.orderId)
    }

}