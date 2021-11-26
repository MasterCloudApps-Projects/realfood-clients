package es.urjc.realfood.clients.infrastructure.api.rest

import es.urjc.realfood.clients.application.RegisterClient
import es.urjc.realfood.clients.application.RegisterClientRequest
import org.springframework.web.bind.annotation.RestController

@RestController
class RegisterRestController(
        private val registerClient: RegisterClient
) : RegisterRestApi {

    override fun register(registerClientRequest: RegisterClientRequest) = registerClient(registerClientRequest)
}