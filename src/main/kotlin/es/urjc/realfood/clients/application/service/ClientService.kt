package es.urjc.realfood.clients.application.service

import es.urjc.realfood.clients.application.repository.ClientRepository
import es.urjc.realfood.clients.application.request.NewClientRequest
import es.urjc.realfood.clients.application.response.ClientDetailResponse
import es.urjc.realfood.clients.application.response.NewClientResponse
import es.urjc.realfood.clients.domain.Client
import es.urjc.realfood.clients.domain.exception.ClientNotFoundException
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class ClientService(
        private val clientRepository: ClientRepository
) {

    fun findAll(): List<ClientDetailResponse> = clientRepository
            .findAll()
            .stream()
            .map { item -> mapToClientDetailResponse(item) }
            .collect(Collectors.toList())

    fun findById(id: String): ClientDetailResponse {
        val client: Client = clientRepository
                .findById(id)
                .orElseThrow { ClientNotFoundException() }
        return mapToClientDetailResponse(client)
    }

    fun save(newClientRequest: NewClientRequest): NewClientResponse {
        // mapear a objeto
        // llamar al cliente de registro - status pending
        // guardar en postgress
        // llamar al cliente de registro - status activado
        // devolver el dto
        TODO("Implementar")
    }

    fun delete(id: String) {
        val client = clientRepository
                .findById(id)
                .orElseThrow { ClientNotFoundException() }
        clientRepository.delete(client)
    }

    private fun mapToClientDetailResponse(client: Client): ClientDetailResponse = ClientDetailResponse(
            id = client.id,
            name = client.name,
            lastName = client.lastName,
            userId = client.userId)

}