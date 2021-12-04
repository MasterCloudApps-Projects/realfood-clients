package es.urjc.realfood.clients.infrastructure.api.rest

import es.urjc.realfood.clients.application.*
import es.urjc.realfood.clients.infrastructure.api.security.JWTValidatorService
import org.springframework.web.bind.annotation.RestController


@RestController
class ClientRestController(
    private val findByIdClient: FindByIdClient,
    private val deleteClient: DeleteClient,
    private val jwtService: JWTValidatorService
) : ClientRestApi {

    override fun whoami(headers: Map<String, String>): FindByIdClientResponse {
        val subject = jwtService.getSubjectFromHeaders(headers)
        return findByIdClient(FindByIdClientRequest(subject))
    }

    override fun unsubscribe(headers: Map<String, String>) {
        val subject = jwtService.getSubjectFromHeaders(headers)
        deleteClient(DeleteClientRequest(subject))
    }

}