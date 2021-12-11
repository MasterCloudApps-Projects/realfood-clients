package es.urjc.realfood.clients.application

import es.urjc.realfood.clients.domain.*
import es.urjc.realfood.clients.domain.repository.CartRepository
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito.mock
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    classes = [
        CartRepository::class
    ]
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ClearCartTest {

    lateinit var cartRepository: CartRepository
    lateinit var clearCart: ClearCart

    @BeforeAll
    fun init() {
        cartRepository = mock(CartRepository::class.java)
        clearCart = ClearCart(cartRepository)
    }

    protected fun validClearCartRequest(): ClearCartRequest {
        return ClearCartRequest(
            clientId = validClientId().toString()
        )
    }

    protected fun invalidClearCartRequest(): ClearCartRequest {
        return ClearCartRequest(
            clientId = "ILLEGAL"
        )
    }

    protected fun validCart(): Cart {
        val cart = Cart(
            id = validCartId(),
            client = validClient()
        )
        cart.items[validItemId()] = validCartItem()
        return cart
    }

    protected fun validClient(): Client {
        return Client(
            id = validClientId(),
            name = Name("Cristofer"),
            lastName = LastName("Lopez"),
            email = Email("cristofer@cristofer.es"),
            password = Password("1234")
        )
    }

    protected fun validCartItem(): CartItem {
        return CartItem(
            itemId = validItemId(),
            quantity = 5
        )
    }

    protected fun validCartId(): CartId = CartId("89a135b8-98dc-4e57-a22f-b5f99c6b1a99")

    protected fun validClientId(): ClientId = ClientId("89a135b8-98dc-4e57-a22f-b5f99c6b1a00")

    protected fun validItemId(): String = "89a135b8-98dc-4e57-a22f-b5f99c6b1a00"

}