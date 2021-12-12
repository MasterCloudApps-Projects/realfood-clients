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
class AddItemToCartTest {

    lateinit var cartRepository: CartRepository
    lateinit var findByIdProductService: FindByIdProductService
    lateinit var addItemToCart: AddItemToCart

    @BeforeAll
    fun init() {
        cartRepository = mock(CartRepository::class.java)
        findByIdProductService = mock(FindByIdProductService::class.java)
        addItemToCart = AddItemToCart(
            cartRepository = cartRepository,
            findByIdProductService = findByIdProductService
        )
    }

    protected fun validAddItemToCartRequest(): AddItemToCartRequest {
        return AddItemToCartRequest(
            clientId = validClientId().toString(),
            itemId = validItemId(),
            quantity = 5
        )
    }

    protected fun invalidIdAddItemToCartRequest(): AddItemToCartRequest {
        return AddItemToCartRequest(
            clientId = "ILLEGAL",
            itemId = validItemId(),
            quantity = 5
        )
    }

    protected fun invalidQuantityAddItemToCartRequest(): AddItemToCartRequest {
        return AddItemToCartRequest(
            clientId = validClientId().toString(),
            itemId = validItemId(),
            quantity = 0
        )
    }

    protected fun validFindByIdProductServiceRequest(): FindByIdProductServiceRequest {
        return FindByIdProductServiceRequest(
            id = validItemId()
        )
    }

    protected fun validFindByIdProductServiceResponse(): FindByIdProductServiceResponse {
        return FindByIdProductServiceResponse(
            status = 200
        )
    }

    protected fun invalidFindByIdProductServiceResponse404(): FindByIdProductServiceResponse {
        return FindByIdProductServiceResponse(
            status = 404
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