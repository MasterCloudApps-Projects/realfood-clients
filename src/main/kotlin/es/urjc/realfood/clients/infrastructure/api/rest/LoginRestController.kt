package es.urjc.realfood.clients.infrastructure.api.rest

import es.urjc.realfood.clients.application.request.ClientCredentialsRequest
import es.urjc.realfood.clients.application.request.NewClientRequest
import es.urjc.realfood.clients.application.response.ClientCredentialsResponse
import es.urjc.realfood.clients.application.service.ClientService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class LoginRestController(
        private val clientService: ClientService
) : LoginRestApi {

    override fun register(newClientRequest: NewClientRequest): ResponseEntity<ClientCredentialsResponse> {
        TODO("Not yet implemented")
    }

    override fun login(clientCredentialsRequest: ClientCredentialsRequest): ResponseEntity<ClientCredentialsResponse> {
        TODO("Not yet implemented")
    }

}