package es.urjc.realfood.clients.infrastructure.api.rest

import es.urjc.realfood.clients.application.AddItemToCartResponse
import es.urjc.realfood.clients.application.CheckoutCartResponse
import es.urjc.realfood.clients.application.FindByClientIdCartResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = "Client cart rest API")
@RequestMapping("/api")
interface CartRestApi {

    @GetMapping("/clients/me/cart")
    fun getCart(@RequestHeader headers: Map<String, String>): FindByClientIdCartResponse

    @DeleteMapping("/clients/me/cart")
    fun clearCart(@RequestHeader headers: Map<String, String>)

    @PostMapping("/clients/me/cart/item")
    fun addItemToCart(
        @RequestHeader headers: Map<String, String>,
        @RequestBody addItemToCartRequest: AddItemToCartRequest
    ): AddItemToCartResponse

    @DeleteMapping("/clients/me/cart/item")
    fun deleteItemFromCart(
        @RequestHeader headers: Map<String, String>,
        @RequestBody deleteItemFromCartRequest: DeleteItemFromCartRequest
    )

    @PostMapping("/clients/me/cart/checkout")
    fun checkoutCart(@RequestHeader headers: Map<String, String>): CheckoutCartResponse

}

data class AddItemToCartRequest(
    val itemId: String,
    val quantity: Int
)

data class DeleteItemFromCartRequest(
    val itemId: String
)