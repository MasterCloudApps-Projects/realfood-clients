package es.urjc.realfood.clients.domain

import java.util.*
import javax.persistence.Embeddable

@Embeddable
class MenuItemId(private val value: String) {

    init {
        UUID.fromString(value)
    }

    override fun toString(): String {
        return value
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MenuItemId

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }


}