package es.urjc.realfood.clients.application

import es.urjc.realfood.clients.domain.ClientId
import es.urjc.realfood.clients.domain.exception.EntityNotFoundException
import es.urjc.realfood.clients.domain.repository.ClientRepository
import es.urjc.realfood.clients.domain.services.DeleteClientEvent
import es.urjc.realfood.clients.domain.services.DeleteClientEventPublisher
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class DeleteClient(
    private val clientRepository: ClientRepository,
    private val deleteClientEventPublisher: DeleteClientEventPublisher
) {

    operator fun invoke(request: DeleteClientRequest) {
        val clientId = ClientId(request.id)
        val client = clientRepository
            .findById(clientId) ?: throw EntityNotFoundException("Client not found")
        clientRepository.delete(client)
        deleteClientEventPublisher(
            DeleteClientEvent(
                clientId = request.id
            )
        )
    }

}

data class DeleteClientRequest(
    val id: String
)