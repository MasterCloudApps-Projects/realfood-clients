package es.urjc.realfood.clients.application

import es.urjc.realfood.clients.domain.*
import es.urjc.realfood.clients.domain.repository.ClientRepository
import es.urjc.realfood.clients.domain.services.JWTService
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito.mock
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@SpringBootTest(
    classes = [
        ClientRepository::class,
        JWTService::class,
        BCryptPasswordEncoder::class
    ]
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class LoginClientTest {

    lateinit var clientRepository: ClientRepository
    lateinit var jwtService: JWTService
    lateinit var bCryptPasswordEncoder: BCryptPasswordEncoder
    lateinit var loginClient: LoginClient

    @BeforeAll
    fun init() {
        clientRepository = mock(ClientRepository::class.java)
        jwtService = mock(JWTService::class.java)
        bCryptPasswordEncoder = mock(BCryptPasswordEncoder::class.java)

        loginClient = LoginClient(
            clientRepository = clientRepository,
            jwtService = jwtService,
            bCryptPasswordEncoder = bCryptPasswordEncoder
        )
    }

    protected fun validLoginClientRequest(): LoginClientRequest {
        return LoginClientRequest(
            email = "cristofer@cristofer.es",
            password = "1234"
        )
    }

    protected fun invalidEmailLoginClientRequest(): LoginClientRequest {
        return LoginClientRequest(
            email = "cristofer",
            password = "1234"
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

    protected fun validEmail(): Email {
        return Email("cristofer@cristofer.es")
    }

}