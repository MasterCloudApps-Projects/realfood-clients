package es.urjc.realfood.clients.application

import es.urjc.realfood.clients.domain.repository.ClientRepository
import es.urjc.realfood.clients.domain.services.AuthService
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    classes = [
        AuthService::class,
        ClientRepository::class
    ]
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RegisterClientTest {

    lateinit var authService: AuthService
    lateinit var clientRepository: ClientRepository
    lateinit var registerClient: RegisterClient

    @BeforeAll
    fun init() {
        authService = Mockito.mock(AuthService::class.java)
        clientRepository = Mockito.mock(ClientRepository::class.java)

        registerClient = RegisterClient(
            authService = authService,
            clientRepository = clientRepository
        )
    }

}