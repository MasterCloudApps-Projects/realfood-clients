package es.urjc.realfood.clients.domain

import java.io.Serializable
import java.util.*
import javax.persistence.Embeddable

@Embeddable
class ClientId(private val value: String) : Serializable {

    init {
        UUID.fromString(value)
    }
}