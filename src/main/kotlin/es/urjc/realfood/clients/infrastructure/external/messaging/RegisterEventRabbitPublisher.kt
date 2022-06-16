package es.urjc.realfood.clients.infrastructure.external.messaging

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import es.urjc.realfood.clients.domain.services.RegisterEvent
import es.urjc.realfood.clients.domain.services.RegisterEventPublisher
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

@Component
class RegisterEventRabbitPublisher(
    private val rabbitTemplate: RabbitTemplate
) : RegisterEventPublisher {

    private val logger = LoggerFactory.getLogger(RegisterEventRabbitPublisher::class.java)

    private val objectMapper: ObjectMapper = ObjectMapper()
        .registerKotlinModule()

    private val queueName: String = "register-queue"
    override fun invoke(registerEvent: RegisterEvent) {
        val msg: String = objectMapper.writeValueAsString(registerEvent)
        rabbitTemplate.convertAndSend(queueName, msg)
        logger.info("[Published] register request sent for user: {}", registerEvent.clientId)
    }

}