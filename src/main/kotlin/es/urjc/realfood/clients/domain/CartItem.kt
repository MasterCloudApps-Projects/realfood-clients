package es.urjc.realfood.clients.domain

import java.util.*
import javax.persistence.Embeddable

@Embeddable
data class CartItem(
    val itemId: String,
    val quantity: Int
) {

    init {
        UUID.fromString(itemId)
    }

}