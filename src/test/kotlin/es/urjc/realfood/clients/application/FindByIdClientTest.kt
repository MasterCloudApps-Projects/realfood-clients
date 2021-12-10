package es.urjc.realfood.clients.application

import es.urjc.realfood.clients.domain.*
import es.urjc.realfood.clients.domain.repository.ClientRepository
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@SpringBootTest(
    classes = [
        ClientRepository::class
    ]
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class FindByIdClientTest {

    lateinit var clientRepository: ClientRepository
    lateinit var findByIdClient: FindByIdClient

    @BeforeAll
    fun init() {
        clientRepository = Mockito.mock(ClientRepository::class.java)

        findByIdClient = FindByIdClient(
            clientRepository = clientRepository
        )
    }

    protected fun validClient(): Client {
        return Client(
            id = ClientId("89a135b8-98dc-4e57-a22f-b5f99c6b1a99"),
            name = Name("Cristofer"),
            lastName = LastName("Lopez"),
            email = Email("cristofer@cristofer.es"),
            password = Password("1234"),
            cart = validCart()
        )
    }

    private fun validCart(): Cart {
        return Cart(CartId(UUID.randomUUID().toString()))
    }

    protected fun validClientId(): ClientId {
        return ClientId("89a135b8-98dc-4e57-a22f-b5f99c6b1a99")
    }

    protected fun validFindByIdClientRequest(): FindByIdClientRequest {
        return FindByIdClientRequest(
            id = "89a135b8-98dc-4e57-a22f-b5f99c6b1a99"
        )
    }

}