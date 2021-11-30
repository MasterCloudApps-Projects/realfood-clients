package es.urjc.realfood.clients.application

import es.urjc.realfood.clients.domain.exception.ClientNotFoundException
import es.urjc.realfood.clients.domain.repository.ClientRepository
import org.springframework.stereotype.Service

@Service
class DeleteClient(
    private val clientRepository: ClientRepository
) {

    operator fun invoke(request: DeleteClientRequest) {
        val client = clientRepository
            .findById(request.id) ?: throw ClientNotFoundException("Client not found")
        clientRepository.delete(client)
    }

}

data class DeleteClientRequest(
    val id: String
)