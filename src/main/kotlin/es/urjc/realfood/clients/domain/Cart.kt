package es.urjc.realfood.clients.domain

import javax.persistence.*

@Entity
class Cart(
    @EmbeddedId
    @AttributeOverride(name = "value", column = Column(name = "id"))
    val id: CartId
) {

    @ElementCollection(fetch = FetchType.EAGER)
    val items = mutableListOf<MenuItemId>()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Cart

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "Cart(id=$id, items=$items)"
    }

}