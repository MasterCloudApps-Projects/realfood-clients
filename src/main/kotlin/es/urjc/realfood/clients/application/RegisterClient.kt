package es.urjc.realfood.clients.application

import es.urjc.realfood.clients.domain.*
import es.urjc.realfood.clients.domain.repository.CartRepository
import es.urjc.realfood.clients.domain.repository.ClientRepository
import es.urjc.realfood.clients.domain.services.AuthService
import es.urjc.realfood.clients.domain.services.JWTService
import es.urjc.realfood.clients.domain.services.RegisterRequest
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
    private val authService: AuthService,
    private val jwtService: JWTService
) {

    operator fun invoke(request: RegisterClientRequest): RegisterClientResponse {
        val validEmail = Email(request.email)
        val clientId = UUID.nameUUIDFromBytes(validEmail.toString().toByteArray()).toString()

        val response = authService(
            RegisterRequest(
                userId = clientId
            )
        )

        if (response.alreadyRegistered)
            throw IllegalArgumentException("User already registered as restaurant")

        val client = clientRepository
            .findById(ClientId(clientId))
        if (client != null)
            throw IllegalArgumentException("User already registered as client")

        val newClientCart = Cart(CartId(UUID.randomUUID().toString()))

        cartRepository.save(newClientCart)

        val newClient = Client(
            id = ClientId(clientId),
            name = Name(request.name),
            lastName = LastName(request.lastName),
            email = validEmail,
            password = Password(bCryptPasswordEncoder.encode(request.password)),
            cart = newClientCart
        )

        clientRepository.save(newClient)

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
