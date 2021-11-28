package es.urjc.realfood.clients.infrastructure.api.rest


import es.urjc.realfood.clients.application.RegisterClientRequest
import es.urjc.realfood.clients.application.RegisterClientResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Tag(name = "Register API")
@RequestMapping("/api")
interface RegisterRestApi {

    @PostMapping("/clients")
    fun register(@RequestBody registerClientRequest: RegisterClientRequest): RegisterClientResponse

}