package es.urjc.realfood.clients.domain

import java.util.*
import javax.persistence.Embedded
import javax.persistence.EmbeddedId
import javax.persistence.Entity

@Entity
class Client(
    @Embedded val name: Name,
    @Embedded val lastName: Name,
    val userId: String
) {

    @EmbeddedId
    val id = ClientId(UUID.randomUUID().toString())

}