package es.urjc.realfood.clients.application

import es.urjc.realfood.clients.domain.*
import es.urjc.realfood.clients.domain.repository.CartRepository
import es.urjc.realfood.clients.domain.repository.OrderRepository
import es.urjc.realfood.clients.domain.services.CheckoutCartService
import es.urjc.realfood.clients.domain.services.CheckoutServiceRequest
import es.urjc.realfood.clients.domain.services.CheckoutServiceResponse
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito.mock
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    classes = [
        CartRepository::class,
        OrderRepository::class
    ]
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CheckoutCartTest {

    lateinit var cartRepository: CartRepository
    lateinit var orderRepository: OrderRepository
    lateinit var checkoutCartService: CheckoutCartService
    lateinit var checkoutCart: CheckoutCart

    @BeforeAll
    fun init() {
        cartRepository = mock(CartRepository::class.java)
        orderRepository = mock(OrderRepository::class.java)
        checkoutCartService = mock(CheckoutCartService::class.java)
        checkoutCart = CheckoutCart(
            cartRepository = cartRepository,
            orderRepository = orderRepository,
            checkoutCartService = checkoutCartService
        )
    }

    protected fun validCheckoutCartRequest(): CheckoutCartRequest {
        return CheckoutCartRequest(
            clientId = validClientId().toString()
        )
    }

    protected fun invalidCheckoutCartRequest(): CheckoutCartRequest {
        return CheckoutCartRequest(
            clientId = "INVALID"
        )
    }

    protected fun validCheckoutServiceRequest(): CheckoutServiceRequest {
        return CheckoutServiceRequest(
            clientId = validClientId().toString(),
            items = listOf(validItemId())
        )
    }

    protected fun validCheckoutServiceResponse(): CheckoutServiceResponse {
        return CheckoutServiceResponse(
            statusCode = 200,
            orderId = validOrderId().toString()
        )
    }

    protected fun invalidCheckoutServiceResponse(): CheckoutServiceResponse {
        return CheckoutServiceResponse(
            statusCode = 400,
            orderId = null
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

    protected fun invalidCart(): Cart {
        return Cart(
            id = validCartId(),
            client = validClient()
        )
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

    protected fun validOrderId(): OrderId = OrderId("89a135b8-98dc-4e57-a22f-b5f99c6b1a11")

    protected fun validItemId(): String = "89a135b8-98dc-4e57-a22f-b5f99c6b1a11"

}