package es.urjc.realfood.clients.infrastructure.external.messaging

import com.fasterxml.jackson.databind.ObjectMapper
import es.urjc.realfood.clients.domain.services.DeleteClientEvent
import es.urjc.realfood.clients.domain.services.DeleteClientEventPublisher
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

@Component
class DeleteClientEventRabbitPublisher(
    private val rabbitTemplate: RabbitTemplate
) : DeleteClientEventPublisher {

    private val logger = LoggerFactory.getLogger(DeleteClientEventRabbitPublisher::class.java)

    private val objectMapper: ObjectMapper = ObjectMapper()

    private val queueName: String = "delete-client-queue"

    override fun invoke(deleteClientEvent: DeleteClientEvent) {
        val msg: String = objectMapper.writeValueAsString(deleteClientEvent)
        rabbitTemplate.convertAndSend(queueName, msg)
        logger.info("[Published] delete client request sent for user: {}", deleteClientEvent.clientId)
    }

}