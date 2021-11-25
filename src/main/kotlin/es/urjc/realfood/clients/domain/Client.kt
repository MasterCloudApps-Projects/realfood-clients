package es.urjc.realfood.clients.domain

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Client(
        val name: String = "default",
        val lastName: String = "default",
        val userId: String = "a2f38804-0064-4528-9421-2605a5879933"
) {

    @Id
    val id = UUID.randomUUID().toString()

    init {
        if (name.isNullOrBlank())
            throw IllegalArgumentException("Name cannot be empty")

        if (lastName.isNullOrBlank())
            throw IllegalArgumentException("Last name cannot be empty")

        UUID.fromString(userId)
    }

}