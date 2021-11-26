package es.urjc.realfood.clients.domain

import java.lang.IllegalArgumentException
import java.util.*
import javax.persistence.Embeddable

@Embeddable
class ClientId(private val value: String) {

    init {
        UUID.fromString(value)
    }
}