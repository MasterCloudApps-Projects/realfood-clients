package es.urjc.realfood.clients.infrastructure.api.rest

import es.urjc.realfood.clients.application.FindByIdClientResponse
import es.urjc.realfood.clients.application.LoginClientRequest
import es.urjc.realfood.clients.application.LoginClientResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = "Clients rest API")
@RequestMapping("/api")
interface ClientRestApi {

    @GetMapping("/clients/me")
    fun whoami(@RequestHeader headers: Map<String, String>): FindByIdClientResponse

    @DeleteMapping("/clients/me")
    fun unsubscribe(@RequestHeader headers: Map<String, String>)

    @PostMapping("/sign-in")
    fun login(@RequestBody loginRequest: LoginClientRequest): LoginClientResponse

}