package es.urjc.realfood.clients.application

import es.urjc.realfood.clients.domain.*
import es.urjc.realfood.clients.domain.repository.ClientRepository
import es.urjc.realfood.clients.domain.services.AuthService
import es.urjc.realfood.clients.domain.services.JWTService
import es.urjc.realfood.clients.domain.services.RegisterRequest
import es.urjc.realfood.clients.domain.services.RegisterResponse
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito.mock
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.*

@SpringBootTest(
    classes = [
        AuthService::class,
        ClientRepository::class
    ]
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class RegisterClientTest {

    lateinit var clientRepository: ClientRepository
    lateinit var bCryptPasswordEncoder: BCryptPasswordEncoder
    lateinit var authService: AuthService
    lateinit var jwtService: JWTService
    lateinit var registerClient: RegisterClient

    @BeforeAll
    fun init() {
        clientRepository = mock(ClientRepository::class.java)
        bCryptPasswordEncoder = mock(BCryptPasswordEncoder::class.java)
        authService = mock(AuthService::class.java)
        jwtService = mock(JWTService::class.java)

        registerClient = RegisterClient(
            clientRepository = clientRepository,
            bCryptPasswordEncoder = bCryptPasswordEncoder,
            authService = authService,
            jwtService = jwtService
        )
    }

    protected fun validClientId(): String {
        return UUID.nameUUIDFromBytes("cristofer@cristofer.es".toByteArray()).toString()
    }

    protected fun validRegisterRequest(): RegisterRequest {
        return RegisterRequest(
            userId = validClientId()
        )
    }

    protected fun validRegisterResponse(): RegisterResponse {
        return RegisterResponse(
            alreadyRegistered = false
        )
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

    protected fun invalidRegisterResponse(): RegisterResponse {
        return RegisterResponse(
            alreadyRegistered = true
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