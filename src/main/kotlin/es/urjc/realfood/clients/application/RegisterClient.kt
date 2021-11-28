package es.urjc.realfood.clients.application

import es.urjc.realfood.clients.domain.Client
import es.urjc.realfood.clients.domain.ClientId
import es.urjc.realfood.clients.domain.LastName
import es.urjc.realfood.clients.domain.Name
import es.urjc.realfood.clients.domain.repository.ClientRepository
import es.urjc.realfood.clients.domain.services.AuthService
import es.urjc.realfood.clients.domain.services.AuthService.Companion.CLIENT_ROLE
import es.urjc.realfood.clients.domain.services.RegisterRequest
import org.springframework.stereotype.Service

@Service
class RegisterClient(
    private val authService: AuthService,
    private val clientRepository: ClientRepository
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
            lastName = LastName(request.lastName)
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
