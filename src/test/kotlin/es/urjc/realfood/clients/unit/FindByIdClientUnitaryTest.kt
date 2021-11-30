package es.urjc.realfood.clients.unit

import es.urjc.realfood.clients.application.FindByIdClientRequest
import es.urjc.realfood.clients.application.FindByIdClientTest
import es.urjc.realfood.clients.domain.exception.ClientNotFoundException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.`when`

class FindByIdClientUnitaryTest : FindByIdClientTest() {

    @Test
    fun givenValidIdWhenAskForUserThenReturnUser() {
        `when`(clientRepository.findById(anyString()))
            .thenReturn(validClientReturned())
        val client = findByIdClient(FindByIdClientRequest("id"))

        assertEquals("Cristofer", client.name)
    }

    @Test
    fun givenUnknownIdWhenAskForUserThenReturnIllegalArgumentException() {
        `when`(clientRepository.findById(anyString()))
            .thenReturn(null)
        val exc = assertThrows(ClientNotFoundException::class.java) {
            findByIdClient(FindByIdClientRequest("id"))
        }

        assertEquals("Client not found", exc.message)
    }

}