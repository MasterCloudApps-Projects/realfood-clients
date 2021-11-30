package es.urjc.realfood.clients.unit

import es.urjc.realfood.clients.application.DeleteClientRequest
import es.urjc.realfood.clients.application.DeleteClientTest
import es.urjc.realfood.clients.domain.exception.ClientNotFoundException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.*

class DeleteClientUnitaryTest : DeleteClientTest() {

    @Test
    fun givenValidIdWhenDeleteUserThenOk() {
        val client = validClientReturned()
        `when`(clientRepository.findById(anyString()))
            .thenReturn(client)
        deleteClient(DeleteClientRequest("id"))

        verify(clientRepository, atLeastOnce()).delete(client)
    }

    @Test
    fun givenUnknownIdWhenDeleteUserThenReturnIllegalArgumentException() {
        `when`(clientRepository.findById(anyString()))
            .thenReturn(null)
        val exc = assertThrows(ClientNotFoundException::class.java) {
            deleteClient(DeleteClientRequest("id"))
        }

        assertEquals("Client not found", exc.message)
    }

}