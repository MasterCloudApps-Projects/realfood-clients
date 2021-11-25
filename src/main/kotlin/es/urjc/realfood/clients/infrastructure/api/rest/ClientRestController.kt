package es.urjc.realfood.clients.infrastructure.api.rest

import es.urjc.realfood.clients.application.request.NewClientRequest
import es.urjc.realfood.clients.application.response.ClientDetailResponse
import es.urjc.realfood.clients.application.response.NewClientResponse
import es.urjc.realfood.clients.application.service.ClientService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class ClientRestController(
        private val clientService: ClientService
) : ClientRestApi {

    override fun findAll(): ResponseEntity<List<ClientDetailResponse>> {
        val list = clientService.findAll()
        return ResponseEntity.ok(list)
    }

    override fun findById(id: String): ResponseEntity<ClientDetailResponse> {
        val client = clientService.findById(id)
        return ResponseEntity.ok(client)
    }

    override fun save(newClientRequest: NewClientRequest): ResponseEntity<NewClientResponse> {
        TODO("Not yet implemented")
    }

    override fun delete(id: String): ResponseEntity<Void> {
        clientService.delete(id)
        return ResponseEntity.ok().build()
    }

}