package es.urjc.realfood.clients.infrastructure.api.rest

import es.urjc.realfood.clients.application.FindAllByClientIdOrdersResponse
import es.urjc.realfood.clients.application.FindByIdAndClientIdOrderResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping

@Tag(name = "Client orders rest API")
@RequestMapping("/api")
interface OrderRestApi {

    @GetMapping("/clients/me/orders")
    fun getOrders(@RequestHeader headers: Map<String, String>): FindAllByClientIdOrdersResponse

    @GetMapping("/clients/me/orders/{orderId}")
    fun getOrder(
        @RequestHeader headers: Map<String, String>,
        @PathVariable orderId: String
    ): FindByIdAndClientIdOrderResponse

}