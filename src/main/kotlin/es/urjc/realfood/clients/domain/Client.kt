package es.urjc.realfood.clients.domain

import javax.persistence.*

@Entity
class Client(
    @EmbeddedId
    @AttributeOverride(name = "value", column = Column(name = "id"))
    val id: ClientId,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "name"))
    val name: Name,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "last_name"))
    val lastName: LastName,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "email"))
    val email: Email,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "password"))
    val password: Password
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Client

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "Client(id=$id, name=$name, lastName=$lastName)"
    }

}