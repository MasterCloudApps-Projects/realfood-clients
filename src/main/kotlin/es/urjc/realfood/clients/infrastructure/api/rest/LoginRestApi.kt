package es.urjc.realfood.clients.infrastructure.api.rest

import es.urjc.realfood.clients.application.request.ClientCredentialsRequest
import es.urjc.realfood.clients.application.request.NewClientRequest
import es.urjc.realfood.clients.application.response.ClientCredentialsResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Tag(name = "Login API")
@RequestMapping("/")
interface LoginRestApi {

    @PostMapping("/register")
    fun register(newClientRequest: NewClientRequest): ResponseEntity<ClientCredentialsResponse>

    @PostMapping("/login")
    fun login(clientCredentialsRequest: ClientCredentialsRequest): ResponseEntity<ClientCredentialsResponse>

}