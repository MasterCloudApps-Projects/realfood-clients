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
    val lastName: LastName
)