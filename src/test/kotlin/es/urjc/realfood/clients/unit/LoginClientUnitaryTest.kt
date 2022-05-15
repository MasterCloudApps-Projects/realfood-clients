package es.urjc.realfood.clients.unit

import es.urjc.realfood.clients.application.LoginClient
import es.urjc.realfood.clients.application.LoginClientTest
import es.urjc.realfood.clients.domain.ClientObjectProvider.Companion.validClient
import es.urjc.realfood.clients.domain.ClientObjectProvider.Companion.validClientIdString
import es.urjc.realfood.clients.domain.ClientObjectProvider.Companion.validEmail
import es.urjc.realfood.clients.domain.repository.ClientRepository
import es.urjc.realfood.clients.domain.services.JWTService
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
        JWTService::class,
        BCryptPasswordEncoder::class
    ]
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LoginClientUnitaryTest : LoginClientTest() {

    lateinit var clientRepository: ClientRepository
    lateinit var jwtService: JWTService
    lateinit var bCryptPasswordEncoder: BCryptPasswordEncoder
    lateinit var loginClient: LoginClient

    @BeforeAll
    fun init() {
        clientRepository = Mockito.mock(ClientRepository::class.java)
        jwtService = Mockito.mock(JWTService::class.java)
        bCryptPasswordEncoder = Mockito.mock(BCryptPasswordEncoder::class.java)

        loginClient = LoginClient(
            clientRepository = clientRepository,
            jwtService = jwtService,
            bCryptPasswordEncoder = bCryptPasswordEncoder
        )
    }

    @Test
    fun `given valid request when login then return jwt`() {
        `when`(clientRepository.findByEmail(validEmail())).thenReturn(validClient())
        `when`(bCryptPasswordEncoder.matches(anyString(), anyString())).thenReturn(true)
        `when`(jwtService.generateJwt(anyString())).thenReturn("token")

        val response = loginClient(validLoginClientRequest())

        assertEquals(validClientIdString(), response.clientId)
        assertEquals("token", response.token)
    }

    @Test
    fun `given invalid email request when login then return illegal argument exception`() {
        val exc = assertThrows(IllegalArgumentException::class.java) {
            loginClient(invalidEmailLoginClientRequest())
        }

        assertEquals("Email 'cristofer' is not a valid email", exc.message)
    }

    @Test
    fun `given invalid password request when login then return illegal argument exception`() {
        `when`(clientRepository.findByEmail(validEmail())).thenReturn(validClient())
        `when`(bCryptPasswordEncoder.matches(anyString(), anyString())).thenReturn(false)

        val exc = assertThrows(IllegalArgumentException::class.java) {
            loginClient(validLoginClientRequest())
        }

        assertEquals("Invalid password", exc.message)
    }

}