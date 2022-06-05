package es.urjc.realfood.clients.application

import es.urjc.realfood.clients.domain.*
import es.urjc.realfood.clients.domain.repository.CartRepository
import es.urjc.realfood.clients.domain.repository.ClientRepository
import es.urjc.realfood.clients.domain.services.JWTService
import es.urjc.realfood.clients.domain.services.RegisterEvent
import es.urjc.realfood.clients.domain.services.RegisterEventPublisher
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
@Transactional
class RegisterClient(
    private val clientRepository: ClientRepository,
    private val cartRepository: CartRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
    private val jwtService: JWTService,
    private val registerEventPublisher: RegisterEventPublisher
) {

    operator fun invoke(request: RegisterClientRequest): RegisterClientResponse {
        val validEmail = Email(request.email)
        val clientId = UUID.nameUUIDFromBytes(validEmail.toString().toByteArray()).toString()

        val client = clientRepository
            .findById(ClientId(clientId))
        if (client != null)
            throw IllegalArgumentException("User already registered")

        val newClient = Client(
            id = ClientId(clientId),
            name = Name(request.name),
            lastName = LastName(request.lastName),
            email = validEmail,
            password = Password(bCryptPasswordEncoder.encode(request.password))
        )

        clientRepository.save(newClient)

        cartRepository.save(
            Cart(
                id = CartId(UUID.randomUUID().toString()),
                client = newClient
            )
        )

        registerEventPublisher(
            RegisterEvent(
                clientId = clientId
            )
        )

        return RegisterClientResponse(
            token = jwtService.generateJwt(clientId),
            clientId = clientId
        )
    }

}

data class RegisterClientResponse(
    val clientId: String,
    val token: String
)

data class RegisterClientRequest(
    val name: String,
    val lastName: String,
    val email: String,
    val password: String
)
