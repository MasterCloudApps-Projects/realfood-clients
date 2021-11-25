package es.urjc.realfood.clients.infrastructure.api.rest

import es.urjc.realfood.clients.application.request.NewClientRequest
import es.urjc.realfood.clients.application.response.ClientDetailResponse
import es.urjc.realfood.clients.application.response.NewClientResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Api de clientes")
@RequestMapping("/api/clients")
interface ClientRestApi {

    @GetMapping("/")
    fun findAll(): ResponseEntity<List<ClientDetailResponse>>

    @GetMapping("/{id}")
    fun findById(@PathVariable id: String): ResponseEntity<ClientDetailResponse>

    @PostMapping("/")
    fun save(@RequestBody newClientRequest: NewClientRequest): ResponseEntity<NewClientResponse>

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<Void>

}