package es.urjc.realfood.clients.domain

import es.urjc.realfood.clients.infrastructure.api.security.JWTGeneratorService
import java.util.*

class ClientObjectProvider {

    companion object {

        fun validClientId(): ClientId = ClientId("89a135b8-98dc-4e57-a22f-b5f99c6b1a00")

        fun validClientIdString(): String = "89a135b8-98dc-4e57-a22f-b5f99c6b1a00"

        fun validClientIdStringFromEmail(): String =
            UUID.nameUUIDFromBytes(validEmailString().toByteArray()).toString()

        fun validClient(): Client {
            return Client(
                id = validClientId(),
                name = Name("Cristofer"),
                lastName = LastName("Lopez"),
                email = Email("cristofer@cristofer.es"),
                password = Password("1234")
            )
        }

        fun validJwt(): String = JWTGeneratorService("1234", "realfood-auth").generateJwt(validClientIdString())

        fun validEmail(): Email {
            return Email("cristofer@cristofer.es")
        }

        fun validEmailString(): String = "cristofer@cristofer.es"
    }

}