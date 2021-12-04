package es.urjc.realfood.clients.application

import es.urjc.realfood.clients.domain.repository.ClientRepository
import es.urjc.realfood.clients.domain.services.AuthService
import es.urjc.realfood.clients.domain.services.RegisterRequest
import es.urjc.realfood.clients.domain.services.RegisterResponse
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
abstract class RegisterClientTest {

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

    protected fun validRegisterClientRequest(): RegisterClientRequest {
        return RegisterClientRequest(
            name = "Cristofer",
            lastName = "Lopez",
            email = "cristofer@email.com",
            password = "1234"
        )
    }

    protected fun invalidNameRegisterClientRequest(): RegisterClientRequest {
        return RegisterClientRequest(
            name = "",
            lastName = "Lopez",
            email = "cristofer@email.com",
            password = "1234"
        )
    }

    protected fun invalidLastNameRegisterClientRequest(): RegisterClientRequest {
        return RegisterClientRequest(
            name = "Cristofer",
            lastName = "",
            email = "cristofer@email.com",
            password = "1234"
        )
    }

    protected fun invalidEmailRegisterClientRequest(): RegisterClientRequest {
        return RegisterClientRequest(
            name = "Cristofer",
            lastName = "Lpez",
            email = "INVALID-EMAIL",
            password = "1234"
        )
    }

    protected fun invalidPasswordRegisterClientRequest(): RegisterClientRequest {
        return RegisterClientRequest(
            name = "Cristofer",
            lastName = "Lpez",
            email = "cristofer@email.com",
            password = ""
        )
    }

    protected fun validRegisterRequest(): RegisterRequest {
        return RegisterRequest(
            email = "cristofer@email.com",
            password = "1234",
            role = "user"
        )
    }

    protected fun validRegisterResponse(): RegisterResponse {
        return RegisterResponse(
            token = "token",
            userId = "89a135b8-98dc-4e57-a22f-b5f99c6b1a99"
        )
    }

}