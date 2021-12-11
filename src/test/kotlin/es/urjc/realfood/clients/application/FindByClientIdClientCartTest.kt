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
class FindByClientIdClientCartTest {

    lateinit var cartRepository: CartRepository
    lateinit var findByClientIdCart: FindByClientIdCart

    @BeforeAll
    fun init() {
        cartRepository = mock(CartRepository::class.java)
        findByClientIdCart = FindByClientIdCart(cartRepository)
    }

    protected fun validFindByClientIdCartRequest(): FindByClientIdCartRequest {
        return FindByClientIdCartRequest(
            clientId = validClientId().toString()
        )
    }

    protected fun invalidFindByClientIdCartRequest(): FindByClientIdCartRequest {
        return FindByClientIdCartRequest(
            clientId = "ILLEGAL"
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

}