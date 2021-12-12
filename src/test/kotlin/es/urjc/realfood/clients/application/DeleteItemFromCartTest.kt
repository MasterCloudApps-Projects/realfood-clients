package es.urjc.realfood.clients.application

import es.urjc.realfood.clients.domain.*
import es.urjc.realfood.clients.domain.repository.CartRepository
import es.urjc.realfood.clients.domain.services.FindByIdProductService
import es.urjc.realfood.clients.domain.services.FindByIdProductServiceRequest
import es.urjc.realfood.clients.domain.services.FindByIdProductServiceResponse
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito.mock
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    classes = [
        CartRepository::class,
        FindByIdProductService::class
    ]
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DeleteItemFromCartTest {

    lateinit var cartRepository: CartRepository
    lateinit var deleteItemFromCart: DeleteItemFromCart

    @BeforeAll
    fun init() {
        cartRepository = mock(CartRepository::class.java)
        deleteItemFromCart = DeleteItemFromCart(
            cartRepository = cartRepository
        )
    }

    protected fun validDeleteItemFromCartRequest(): DeleteItemFromCartRequest {
        return DeleteItemFromCartRequest(
            clientId = validClientId().toString(),
            itemId = validItemId()
        )
    }

    protected fun invalidDeleteItemFromCartRequest(): DeleteItemFromCartRequest {
        return DeleteItemFromCartRequest(
            clientId = "ILLEGAL",
            itemId = validItemId()
        )
    }

    protected fun validCart(): Cart {
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

    protected fun validCartId(): CartId = CartId("89a135b8-98dc-4e57-a22f-b5f99c6b1a99")

    protected fun validClientId(): ClientId = ClientId("89a135b8-98dc-4e57-a22f-b5f99c6b1a00")

    protected fun validItemId(): String = "89a135b8-98dc-4e57-a22f-b5f99c6b1a00"

}