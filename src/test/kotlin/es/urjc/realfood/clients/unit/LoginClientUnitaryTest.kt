package es.urjc.realfood.clients.unit

import es.urjc.realfood.clients.application.LoginClientTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyString

class LoginClientUnitaryTest : LoginClientTest() {

    @Test
    fun `given valid request when login then return jwt`() {
        `when`(
            clientRepository.findByEmail(
                email = validEmail()
            )
        ).thenReturn(validClient())
        `when`(bCryptPasswordEncoder.matches(anyString(), anyString())).thenReturn(true)
        `when`(jwtService.generateJwt(anyString())).thenReturn("token")

        val response = loginClient(validLoginClientRequest())

        assertEquals("89a135b8-98dc-4e57-a22f-b5f99c6b1a99", response.clientId)
        assertEquals("token", response.token)
    }

    @Test
    fun `given invalid email request when login then return illegal argument exception`() {
        val exc = Assertions.assertThrows(IllegalArgumentException::class.java) {
            loginClient(invalidEmailLoginClientRequest())
        }

        assertEquals("Email 'cristofer' is not valid email", exc.message)
    }

    @Test
    fun `given invalid password request when login then return illegal argument exception`() {
        `when`(
            clientRepository.findByEmail(
                email = validEmail()
            )
        ).thenReturn(validClient())
        `when`(bCryptPasswordEncoder.matches(anyString(), anyString())).thenReturn(false)

        val exc = Assertions.assertThrows(IllegalArgumentException::class.java) {
            loginClient(validLoginClientRequest())
        }

        assertEquals("Invalid password", exc.message)
    }

}