package es.urjc.realfood.clients.unit

import es.urjc.realfood.clients.application.RegisterClientRequest
import es.urjc.realfood.clients.application.RegisterClientTest
import es.urjc.realfood.clients.domain.services.RegisterRequest
import es.urjc.realfood.clients.domain.services.RegisterResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`

class RegisterClientUnitaryTest : RegisterClientTest() {

    @Test
    fun givenValidRequestWhenRegisterThenReturnJwt() {
        `when`(authService(validRegisterRequest()))
            .thenReturn(
                RegisterResponse(
                    token = "token",
                    userId = "89a135b8-98dc-4e57-a22f-b5f99c6b1a99"
                )
            )
        val response = registerClient(validRegisterClientRequest())

        assertEquals("token", response.token)
        assertEquals("89a135b8-98dc-4e57-a22f-b5f99c6b1a99", response.clientId)
    }

    @Test
    fun givenInvalidNameInRequestWhenRegisterThenThrowExc() {
        `when`(authService(validRegisterRequest()))
            .thenReturn(
                RegisterResponse(
                    token = "token",
                    userId = "89a135b8-98dc-4e57-a22f-b5f99c6b1a99"
                )
            )
        val exc = assertThrows(IllegalArgumentException::class.java) {
            registerClient(invalidNameRegisterClientRequest())
        }

        assertEquals("Name cannot be empty", exc.message)
    }

    private fun validRegisterClientRequest(): RegisterClientRequest {
        return RegisterClientRequest(
            name = "Cristofer",
            lastName = "Lopez",
            email = "cristofer@email.com",
            password = "1234"
        )
    }

    private fun invalidNameRegisterClientRequest(): RegisterClientRequest {
        return RegisterClientRequest(
            name = "",
            lastName = "Lopez",
            email = "cristofer@email.com",
            password = "1234"
        )
    }

    private fun validRegisterRequest(): RegisterRequest {
        return RegisterRequest(
            email = "cristofer@email.com",
            password = "1234",
            role = "user"
        )
    }

}