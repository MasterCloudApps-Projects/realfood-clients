package es.urjc.realfood.clients.domain.services

interface CheckoutCartService {

    operator fun invoke(request: CheckoutServiceRequest): CheckoutServiceResponse

}

data class CheckoutServiceRequest(
    val clientId: String,
    val items: List<CartItemDto>
)

data class CartItemDto(
    val item: String,
    val quantity: Int
)

data class CheckoutServiceResponse(
    val statusCode: Int,
    val orderId: String?
)