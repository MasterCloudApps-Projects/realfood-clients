package es.urjc.realfood.clients.infrastructure.api.rest

import es.urjc.realfood.clients.application.FindByIdClientResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping

@Tag(name = "Clients rest API")
@RequestMapping("/api")
interface ClientRestApi {

    @GetMapping("/me")
    fun whoami(@RequestHeader headers: Map<String, String>): FindByIdClientResponse

    @DeleteMapping("/unsubscribe")
    fun unsubscribe(@RequestHeader headers: Map<String, String>)

}