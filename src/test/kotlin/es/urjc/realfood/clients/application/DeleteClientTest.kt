package es.urjc.realfood.clients.application

import es.urjc.realfood.clients.domain.*
import es.urjc.realfood.clients.domain.repository.ClientRepository
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    classes = [
        ClientRepository::class
    ]
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class DeleteClientTest {

    lateinit var clientRepository: ClientRepository
    lateinit var deleteClient: DeleteClient

    @BeforeAll
    fun init() {
        clientRepository = Mockito.mock(ClientRepository::class.java)

        deleteClient = DeleteClient(
            clientRepository = clientRepository
        )
    }

    protected fun validClient(): Client {
        return Client(
            id = ClientId("89a135b8-98dc-4e57-a22f-b5f99c6b1a99"),
            name = Name("Cristofer"),
            lastName = LastName("Lopez"),
            email = Email("cristofer@cristofer.es"),
            password = Password("1234")
        )
    }

    protected fun validClientId(): ClientId {
        return ClientId("89a135b8-98dc-4e57-a22f-b5f99c6b1a99")
    }

    protected fun validDeleteClientRequest(): DeleteClientRequest {
        return DeleteClientRequest(
            id = "89a135b8-98dc-4e57-a22f-b5f99c6b1a99"
        )
    }

}