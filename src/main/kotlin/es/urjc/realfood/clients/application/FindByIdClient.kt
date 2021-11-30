package es.urjc.realfood.clients.application

import es.urjc.realfood.clients.domain.exception.ClientNotFoundException
import es.urjc.realfood.clients.domain.repository.ClientRepository
import org.springframework.stereotype.Service

@Service
class FindByIdClient(
    private val clientRepository: ClientRepository
) {

    operator fun invoke(request: FindByIdClientRequest): FindByIdClientResponse {
        val client = clientRepository
            .findById(request.id) ?: throw ClientNotFoundException("Client not found")
        return FindByIdClientResponse(
            id = client.id.toString(),
            name = client.name.toString(),
            lastName = client.lastName.toString(),
            email = ""
        )
    }

}

data class FindByIdClientRequest(
    val id: String
)

data class FindByIdClientResponse(
    val id: String,
    val name: String,
    val lastName: String,
    val email: String
)