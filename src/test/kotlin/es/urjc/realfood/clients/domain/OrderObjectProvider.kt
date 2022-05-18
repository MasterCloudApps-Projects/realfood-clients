package es.urjc.realfood.clients.domain

import es.urjc.realfood.clients.domain.ClientObjectProvider.Companion.validClient
import es.urjc.realfood.clients.domain.ClientObjectProvider.Companion.validClientIdString
import es.urjc.realfood.clients.domain.services.PaymentEvent
import es.urjc.realfood.clients.domain.services.PreparationEvent

class OrderObjectProvider {

    companion object {

        fun validOrderId(): OrderId = OrderId("89a135b8-98dc-4e57-a22f-b5f99c6b1a11")

        fun validOrderIdString(): String = "89a135b8-98dc-4e57-a22f-b5f99c6b1a11"

        fun validPrice(): Double = 20.0

        fun validOrder(): Order {
            return Order(
                id = validOrderId(),
                status = pendingStatus(),
                client = validClient(),
                price = validPrice()
            )
        }

        fun validPaymentEvent(): PaymentEvent {
            return PaymentEvent(
                clientId = validClientIdString(),
                orderId = validOrderIdString(),
                price = validPrice()
            )
        }

        fun validPreparationEvent(): PreparationEvent {
            return PreparationEvent(
                orderId = validOrderIdString()
            )
        }

        fun inProgressStatus() = Status.IN_PROGRESS

        fun pendingStatus() = Status.PENDING

    }

}