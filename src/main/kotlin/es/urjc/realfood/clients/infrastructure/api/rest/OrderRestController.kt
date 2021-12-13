package es.urjc.realfood.clients.infrastructure.api.rest

import es.urjc.realfood.clients.application.*
import es.urjc.realfood.clients.infrastructure.api.security.JWTValidatorService
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderRestController(
    private val findAllByClientIdOrders: FindAllByClientIdOrders,
    private val findByIdAndClientIdOrder: FindByIdAndClientIdOrder,
    private val jwtValidatorService: JWTValidatorService
) : OrderRestApi {

    override fun getOrders(headers: Map<String, String>): FindAllByClientIdOrdersResponse {
        val subject = jwtValidatorService.getSubjectFromHeaders(headers)
        return findAllByClientIdOrders(FindAllByClientIdOrdersRequest(subject))
    }

    override fun getOrder(headers: Map<String, String>, orderId: String): FindByIdAndClientIdOrderResponse {
        val subject = jwtValidatorService.getSubjectFromHeaders(headers)
        return findByIdAndClientIdOrder(
            FindByIdAndClientIdOrderRequest(
                clientId = subject,
                orderId = orderId
            )
        )
    }

}