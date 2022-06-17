package es.urjc.realfood.clients.infrastructure.external.services

import es.urjc.realfood.clients.domain.services.CheckoutCartService
import es.urjc.realfood.clients.domain.services.CheckoutServiceRequest
import es.urjc.realfood.clients.domain.services.CheckoutServiceResponse
import java.util.*

class FakeCheckoutCartService : CheckoutCartService {

    override fun invoke(request: CheckoutServiceRequest, token: String): CheckoutServiceResponse {
        return CheckoutServiceResponse(
            orderId = UUID.randomUUID().toString(),
            total = 20.0
        )
    }

}