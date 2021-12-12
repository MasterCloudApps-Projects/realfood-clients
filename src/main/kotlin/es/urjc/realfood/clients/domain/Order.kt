package es.urjc.realfood.clients.domain

import javax.persistence.AttributeOverride
import javax.persistence.Column
import javax.persistence.EmbeddedId
import javax.persistence.ManyToOne

class Order(
    @EmbeddedId
    @AttributeOverride(name = "value", column = Column(name = "id"))
    val id: OrderId,
    
    val status: String,

    @ManyToOne
    val client: Client
) {

    override fun toString(): String {
        return "Order(id=$id, client=$client)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Order

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}