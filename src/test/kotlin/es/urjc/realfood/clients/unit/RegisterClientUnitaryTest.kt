package es.urjc.realfood.clients.unit

import es.urjc.realfood.clients.application.RegisterClientTest
import es.urjc.realfood.clients.domain.ClientId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyString

class RegisterClientUnitaryTest : RegisterClientTest() {

    @Test
    fun `given valid request when register then return jwt`() {
        `when`(authService(validRegisterRequest())).thenReturn(validRegisterResponse())
        `when`(clientRepository.findById(ClientId(validClientId()))).thenReturn(null)
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
        `when`(authService(validRegisterRequest())).thenReturn(validRegisterResponse())
        `when`(clientRepository.findById(ClientId(validClientId()))).thenReturn(null)

        val exc = assertThrows(IllegalArgumentException::class.java) {
            registerClient(invalidNameRegisterClientRequest())
        }

        assertEquals("Name cannot be empty", exc.message)
    }

    @Test
    fun `given invalid last name in request when register then throw illegal arg exception`() {
        `when`(authService(validRegisterRequest())).thenReturn(validRegisterResponse())
        `when`(clientRepository.findById(ClientId(validClientId()))).thenReturn(null)

        val exc = assertThrows(IllegalArgumentException::class.java) {
            registerClient(invalidLastNameRegisterClientRequest())
        }

        assertEquals("Last name cannot be empty", exc.message)
    }

    @Test
    fun `given invalid password in request when register then throw illegal arg exception`() {
        `when`(authService(validRegisterRequest())).thenReturn(validRegisterResponse())
        `when`(clientRepository.findById(ClientId(validClientId()))).thenReturn(null)
        `when`(bCryptPasswordEncoder.encode(anyString())).thenReturn("")

        val exc = assertThrows(IllegalArgumentException::class.java) {
            registerClient(validRegisterClientRequest())
        }

        assertEquals("Password cannot be empty", exc.message)
    }

    @Test
    fun `given already registered restaurant in request when register then throw illegal arg exception`() {
        `when`(authService(validRegisterRequest())).thenReturn(invalidRegisterResponse())

        val exc = assertThrows(IllegalArgumentException::class.java) {
            registerClient(validRegisterClientRequest())
        }

        assertEquals("User already registered as restaurant", exc.message)
    }

    @Test
    fun `given already registered client in request when register then throw illegal arg exception`() {
        `when`(authService(validRegisterRequest())).thenReturn(validRegisterResponse())
        `when`(clientRepository.findById(ClientId(validClientId()))).thenReturn(registeredClient())

        val exc = assertThrows(IllegalArgumentException::class.java) {
            registerClient(validRegisterClientRequest())
        }

        assertEquals("User already registered as client", exc.message)
    }

}