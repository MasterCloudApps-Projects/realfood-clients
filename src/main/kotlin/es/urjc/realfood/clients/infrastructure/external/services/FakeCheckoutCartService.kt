package es.urjc.realfood.clients.infrastructure.external.services

import es.urjc.realfood.clients.domain.services.CheckoutCartService
import es.urjc.realfood.clients.domain.services.CheckoutServiceRequest
import es.urjc.realfood.clients.domain.services.CheckoutServiceResponse
import org.springframework.stereotype.Component
import java.util.*

@Component
class FakeCheckoutCartService : CheckoutCartService {

    override fun invoke(request: CheckoutServiceRequest): CheckoutServiceResponse {
        return CheckoutServiceResponse(
            statusCode = 200,
            orderId = UUID.randomUUID().toString()
        )
    }

}