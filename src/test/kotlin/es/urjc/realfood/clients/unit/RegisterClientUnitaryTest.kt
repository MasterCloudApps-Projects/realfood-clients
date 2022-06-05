package es.urjc.realfood.clients.unit

import es.urjc.realfood.clients.application.RegisterClient
import es.urjc.realfood.clients.application.RegisterClientTest
import es.urjc.realfood.clients.domain.ClientId
import es.urjc.realfood.clients.domain.ClientObjectProvider.Companion.validClient
import es.urjc.realfood.clients.domain.ClientObjectProvider.Companion.validClientIdStringFromEmail
import es.urjc.realfood.clients.domain.repository.CartRepository
import es.urjc.realfood.clients.domain.repository.ClientRepository
import es.urjc.realfood.clients.domain.services.JWTService
import es.urjc.realfood.clients.domain.services.RegisterEventPublisher
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyString
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@SpringBootTest(
    classes = [
        ClientRepository::class,
        CartRepository::class
    ]
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RegisterClientUnitaryTest : RegisterClientTest() {

    lateinit var clientRepository: ClientRepository
    lateinit var cartRepository: CartRepository
    lateinit var bCryptPasswordEncoder: BCryptPasswordEncoder
    lateinit var jwtService: JWTService
    lateinit var registerEventPublisher: RegisterEventPublisher
    lateinit var registerClient: RegisterClient

    @BeforeAll
    fun init() {
        clientRepository = Mockito.mock(ClientRepository::class.java)
        cartRepository = Mockito.mock(CartRepository::class.java)
        bCryptPasswordEncoder = Mockito.mock(BCryptPasswordEncoder::class.java)
        jwtService = Mockito.mock(JWTService::class.java)
        registerEventPublisher = Mockito.mock(RegisterEventPublisher::class.java)

        registerClient = RegisterClient(
            clientRepository = clientRepository,
            cartRepository = cartRepository,
            bCryptPasswordEncoder = bCryptPasswordEncoder,
            jwtService = jwtService,
            registerEventPublisher = registerEventPublisher
        )
    }

    @Test
    fun `given valid request when register then return jwt`() {
        `when`(clientRepository.findById(ClientId(validClientIdStringFromEmail()))).thenReturn(null)
        `when`(bCryptPasswordEncoder.encode(anyString())).thenReturn("1234")
        `when`(jwtService.generateJwt(anyString())).thenReturn("token")

        val response = registerClient(validRegisterClientRequest())

        assertEquals("token", response.token)
    }

    @Test
    fun `given invalid email in request when register then throw illegal arg exception`() {
        val exc = assertThrows(IllegalArgumentException::class.java) {
            registerClient(invalidEmailRegisterClientRequest())
        }

        assertEquals("Email '' is not a valid email", exc.message)
    }

    @Test
    fun `given invalid name in request when register then throw illegal arg exception`() {
        `when`(clientRepository.findById(ClientId(validClientIdStringFromEmail()))).thenReturn(null)

        val exc = assertThrows(IllegalArgumentException::class.java) {
            registerClient(invalidNameRegisterClientRequest())
        }

        assertEquals("Name cannot be empty", exc.message)
    }

    @Test
    fun `given invalid last name in request when register then throw illegal arg exception`() {
        `when`(clientRepository.findById(ClientId(validClientIdStringFromEmail()))).thenReturn(null)

        val exc = assertThrows(IllegalArgumentException::class.java) {
            registerClient(invalidLastNameRegisterClientRequest())
        }

        assertEquals("Last name cannot be empty", exc.message)
    }

    @Test
    fun `given invalid password in request when register then throw illegal arg exception`() {
        `when`(clientRepository.findById(ClientId(validClientIdStringFromEmail()))).thenReturn(null)
        `when`(bCryptPasswordEncoder.encode(anyString())).thenReturn("")

        val exc = assertThrows(IllegalArgumentException::class.java) {
            registerClient(validRegisterClientRequest())
        }

        assertEquals("Password cannot be empty", exc.message)
    }

    @Test
    fun `given already registered client in request when register then throw illegal arg exception`() {
        `when`(clientRepository.findById(ClientId(validClientIdStringFromEmail()))).thenReturn(validClient())

        val exc = assertThrows(IllegalArgumentException::class.java) {
            registerClient(validRegisterClientRequest())
        }

        assertEquals("User already registered", exc.message)
    }

}