package es.urjc.realfood.clients.domain.services

interface DeleteClientEventPublisher {

    operator fun invoke(deleteClientEvent: DeleteClientEvent)

}

data class DeleteClientEvent(
    val clientId: String
)