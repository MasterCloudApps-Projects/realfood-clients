package es.urjc.realfood.clients.application

import es.urjc.realfood.clients.domain.Client
import es.urjc.realfood.clients.domain.ClientId
import es.urjc.realfood.clients.domain.LastName
import es.urjc.realfood.clients.domain.Name
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
class DeleteClientTest {

    lateinit var clientRepository: ClientRepository
    lateinit var deleteClient: DeleteClient

    @BeforeAll
    fun init() {
        clientRepository = Mockito.mock(ClientRepository::class.java)

        deleteClient = DeleteClient(
            clientRepository = clientRepository
        )
    }

    protected fun validClientReturned(): Client {
        return Client(
            id = ClientId("89a135b8-98dc-4e57-a22f-b5f99c6b1a99"),
            name = Name("Cristofer"),
            lastName = LastName("Lopez")
        )
    }

}