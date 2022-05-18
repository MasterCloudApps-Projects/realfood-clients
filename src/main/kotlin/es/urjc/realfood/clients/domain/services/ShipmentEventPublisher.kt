package es.urjc.realfood.clients.domain.services

interface ShipmentEventPublisher {

    operator fun invoke(shipmentEvent: ShipmentEvent)

}

data class ShipmentEvent(
    val clientId: String,
    val orderId: String
)