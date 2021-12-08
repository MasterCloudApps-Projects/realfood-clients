package es.urjc.realfood.clients

import es.urjc.realfood.clients.domain.*
import es.urjc.realfood.clients.domain.repository.ClientRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import java.util.*
import javax.annotation.PostConstruct

@Component
class DatabaseInitializer(
    private val clientRepository: ClientRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder
) {

    @PostConstruct
    fun setUp() {
        clientRepository.save(
            Client(
                id = ClientId(UUID.nameUUIDFromBytes("cristofer@cristofer.es".toByteArray()).toString()),
                name = Name("Cristofer"),
                lastName = LastName("Lopez"),
                email = Email("cristofer@cristofer.es"),
                password = Password(bCryptPasswordEncoder.encode("1234"))
            )
        )
        clientRepository.save(
            Client(
                id = ClientId(UUID.nameUUIDFromBytes("juan@juan.es".toByteArray()).toString()),
                name = Name("juan"),
                lastName = LastName("Avila"),
                email = Email("juan@juan.es"),
                password = Password(bCryptPasswordEncoder.encode("1234"))
            )
        )
    }

}