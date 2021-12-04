package es.urjc.realfood.clients.application

import es.urjc.realfood.clients.domain.*
import es.urjc.realfood.clients.domain.repository.ClientRepository
import es.urjc.realfood.clients.domain.services.AuthService
import es.urjc.realfood.clients.domain.services.AuthService.Companion.CLIENT_ROLE
import es.urjc.realfood.clients.domain.services.RegisterRequest
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class RegisterClient(
    private val authService: AuthService,
    private val clientRepository: ClientRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder
) {

    operator fun invoke(request: RegisterClientRequest): RegisterClientResponse {
        val response = authService(
            RegisterRequest(
                email = request.email,
                password = request.password,
                role = CLIENT_ROLE
            )
        )

        val newClient = Client(
            id = ClientId(response.userId),
            name = Name(request.name),
            lastName = LastName(request.lastName),
            email = Email(request.email),
            password = Password(bCryptPasswordEncoder.encode(request.password))
        )

        clientRepository.save(newClient)

        return RegisterClientResponse(
            token = response.token,
            clientId = response.userId
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
