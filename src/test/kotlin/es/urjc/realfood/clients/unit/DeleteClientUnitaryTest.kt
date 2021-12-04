package es.urjc.realfood.clients.unit

import es.urjc.realfood.clients.application.DeleteClientTest
import es.urjc.realfood.clients.domain.exception.ClientNotFoundException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

class DeleteClientUnitaryTest : DeleteClientTest() {

    @Test
    fun givenValidIdWhenDeleteUserThenOk() {
        val client = validClient()

        `when`(clientRepository.findById(validClientId()))
            .thenReturn(client)

        deleteClient(validDeleteClientRequest())

        verify(clientRepository, atLeastOnce()).delete(client)
    }

    @Test
    fun givenUnknownIdWhenDeleteUserThenReturnIllegalArgumentException() {
        `when`(clientRepository.findById(validClientId()))
            .thenReturn(null)

        val exc = assertThrows(ClientNotFoundException::class.java) {
            deleteClient((validDeleteClientRequest()))
        }

        assertEquals("Client not found", exc.message)
    }

}