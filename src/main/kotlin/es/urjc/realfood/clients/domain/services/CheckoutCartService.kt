package es.urjc.realfood.clients.domain.services

interface CheckoutCartService {

    operator fun invoke(request: CheckoutServiceRequest, token: String): CheckoutServiceResponse

}

data class CheckoutServiceRequest(
    val clientId: String,
    val restaurantId: String,
    val items: List<CartItemDto>
)

data class CartItemDto(
    val itemId: String,
    val qty: Int
)

data class CheckoutServiceResponse(
    val orderId: String,
    val total: Double?
)