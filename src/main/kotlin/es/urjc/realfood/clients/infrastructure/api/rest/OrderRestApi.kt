package es.urjc.realfood.clients.infrastructure.api.rest

import es.urjc.realfood.clients.application.FindAllByClientIdOrdersResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = "Client orders rest API")
@RequestMapping("/api")
interface OrderRestApi {

    @GetMapping("/clients/me/orders")
    fun getOrders(@RequestHeader headers: Map<String, String>): FindAllByClientIdOrdersResponse

    @DeleteMapping("/clients/me/orders/{orderId}")
    fun getOrder(@RequestHeader headers: Map<String, String>, @PathVariable orderId: String)

}