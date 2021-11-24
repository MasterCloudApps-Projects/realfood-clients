package es.urjc.realfood.clients.domain

import java.lang.IllegalArgumentException
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Client(
        val name: String,
        val lastName: String,
        val userId: String
) {

    @Id
    val id = UUID.randomUUID().toString()

    init {
        if(name.isNullOrBlank())
            throw IllegalArgumentException("Name cannot be empty")

        if(lastName.isNullOrBlank())
            throw IllegalArgumentException("Last name cannot be empty")

        UUID.fromString(userId)
    }

}