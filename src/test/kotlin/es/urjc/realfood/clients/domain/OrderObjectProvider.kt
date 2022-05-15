package es.urjc.realfood.clients.domain

import es.urjc.realfood.clients.domain.ClientObjectProvider.Companion.validClient

class OrderObjectProvider {

    companion object {

        fun validOrderId(): OrderId = OrderId("89a135b8-98dc-4e57-a22f-b5f99c6b1a11")

        fun validOrderIdString(): String = "89a135b8-98dc-4e57-a22f-b5f99c6b1a11"

        fun validOrder(): Order {
            return Order(
                id = validOrderId(),
                status = Status.PENDING,
                client = validClient()
            )
        }

    }

}