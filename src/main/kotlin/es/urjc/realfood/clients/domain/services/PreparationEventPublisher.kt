package es.urjc.realfood.clients.domain.services

interface PreparationEventPublisher {

    operator fun invoke(preparationEvent: PreparationEvent)

}

data class PreparationEvent(
    val orderId: String
)