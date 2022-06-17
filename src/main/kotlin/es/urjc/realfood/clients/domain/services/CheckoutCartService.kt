package es.urjc.realfood.clients.domain.services

interface CheckoutCartService {

    operator fun invoke(request: CheckoutServiceRequest, token: String): CheckoutServiceResponse

}

data class CheckoutServiceRequest(
    val restaurantId: String,
    val lines: List<CartItemDto>
)

data class CartItemDto(
    val itemId: String,
    val qty: Int
)

data class CheckoutServiceResponse(
    val orderId: String,
    val total: Double?
)