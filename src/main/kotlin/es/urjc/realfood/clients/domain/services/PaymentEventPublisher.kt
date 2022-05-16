package es.urjc.realfood.clients.domain.services

interface PaymentEventPublisher {

    operator fun invoke(paymentEvent: PaymentEvent)

}

data class PaymentEvent(
    val clientId: String,
    val orderId: String,
    val price: Double
)