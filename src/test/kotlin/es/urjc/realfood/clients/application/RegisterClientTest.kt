package es.urjc.realfood.clients.application

import es.urjc.realfood.clients.domain.*
import es.urjc.realfood.clients.domain.repository.CartRepository
import es.urjc.realfood.clients.domain.repository.ClientRepository
import es.urjc.realfood.clients.domain.services.JWTService
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito.mock
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.*

@SpringBootTest(
    classes = [
        ClientRepository::class,
        CartRepository::class
    ]
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class RegisterClientTest {

    lateinit var clientRepository: ClientRepository
    lateinit var cartRepository: CartRepository
    lateinit var bCryptPasswordEncoder: BCryptPasswordEncoder
    lateinit var jwtService: JWTService
    lateinit var registerClient: RegisterClient

    @BeforeAll
    fun init() {
        clientRepository = mock(ClientRepository::class.java)
        cartRepository = mock(CartRepository::class.java)
        bCryptPasswordEncoder = mock(BCryptPasswordEncoder::class.java)
        jwtService = mock(JWTService::class.java)

        registerClient = RegisterClient(
            clientRepository = clientRepository,
            cartRepository = cartRepository,
            bCryptPasswordEncoder = bCryptPasswordEncoder,
            jwtService = jwtService
        )
    }

    protected fun validClientId(): String {
        return UUID.nameUUIDFromBytes("cristofer@cristofer.es".toByteArray()).toString()
    }

    protected fun registeredClient(): Client {
        return Client(
            id = ClientId("89a135b8-98dc-4e57-a22f-b5f99c6b1a99"),
            name = Name("Cristofer"),
            lastName = LastName("Lopez"),
            email = Email("cristofer@cristofer.es"),
            password = Password("1234")
        )
    }

    protected fun validRegisterClientRequest(): RegisterClientRequest {
        return RegisterClientRequest(
            name = "Cristofer",
            lastName = "Lopez",
            email = "cristofer@cristofer.es",
            password = "1234"
        )
    }

    protected fun invalidEmailRegisterClientRequest(): RegisterClientRequest {
        return RegisterClientRequest(
            name = "Cristofer",
            lastName = "Lopez",
            email = "",
            password = "1234"
        )
    }

    protected fun invalidNameRegisterClientRequest(): RegisterClientRequest {
        return RegisterClientRequest(
            name = "",
            lastName = "Lopez",
            email = "cristofer@cristofer.es",
            password = "1234"
        )
    }

    protected fun invalidLastNameRegisterClientRequest(): RegisterClientRequest {
        return RegisterClientRequest(
            name = "Cristofer",
            lastName = "",
            email = "cristofer@cristofer.es",
            password = "1234"
        )
    }

}