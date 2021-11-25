package es.urjc.realfood.clients.infrastructure.api.rest

import es.urjc.realfood.clients.application.response.ClientDetailResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@Tag(name = "Clients API")
@RequestMapping("/api/clients")
interface ClientRestApi {

    @GetMapping("/")
    fun findAll(): ResponseEntity<List<ClientDetailResponse>>

    @GetMapping("/{id}")
    fun findById(@PathVariable id: String): ResponseEntity<ClientDetailResponse>

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<Void>

}