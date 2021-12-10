package es.urjc.realfood.clients.domain

import java.util.*
import javax.persistence.Embeddable

@Embeddable
class CartItem(
    private val itemId: String,
    private val quantity: Int
) {

    init {
        UUID.fromString(itemId)
    }

    override fun toString(): String {
        return "CartItem(itemId='$itemId', quantity=$quantity)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CartItem

        if (itemId != other.itemId) return false

        return true
    }

    override fun hashCode(): Int {
        return itemId.hashCode()
    }

}