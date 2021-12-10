package es.urjc.realfood.clients.application

import es.urjc.realfood.clients.domain.ClientId
import es.urjc.realfood.clients.domain.exception.EntityNotFoundException
import es.urjc.realfood.clients.domain.repository.ClientRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class DeleteClient(
    private val clientRepository: ClientRepository
) {

    operator fun invoke(request: DeleteClientRequest) {
        val clientId = ClientId(request.id)
        val client = clientRepository
            .findById(clientId) ?: throw EntityNotFoundException("Client not found")
        clientRepository.delete(client)
    }

}

data class DeleteClientRequest(
    val id: String
)