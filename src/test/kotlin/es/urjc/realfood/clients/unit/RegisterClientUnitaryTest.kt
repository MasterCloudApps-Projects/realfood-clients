package es.urjc.realfood.clients.unit

import es.urjc.realfood.clients.application.RegisterClientTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`

class RegisterClientUnitaryTest : RegisterClientTest() {

    @Test
    fun `given valid request when register then return jwt`() {
        `when`(authService(validRegisterRequest()))
            .thenReturn(validRegisterResponse())

        val response = registerClient(validRegisterClientRequest())

        assertEquals("token", response.token)
        assertEquals("89a135b8-98dc-4e57-a22f-b5f99c6b1a99", response.clientId)
    }

    @Test
    fun `given invalid name in request when register then throw illegal arg exception`() {
        `when`(authService(validRegisterRequest()))
            .thenReturn(validRegisterResponse())

        val exc = assertThrows(IllegalArgumentException::class.java) {
            registerClient(invalidNameRegisterClientRequest())
        }

        assertEquals("Name cannot be empty", exc.message)
    }

    @Test
    fun `given invalid last name in request when register then throw illegal arg exception`() {
        `when`(authService(validRegisterRequest()))
            .thenReturn(validRegisterResponse())

        val exc = assertThrows(IllegalArgumentException::class.java) {
            registerClient(invalidLastNameRegisterClientRequest())
        }

        assertEquals("Last name cannot be empty", exc.message)
    }

}