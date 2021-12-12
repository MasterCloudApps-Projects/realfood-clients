package es.urjc.realfood.clients.unit

import es.urjc.realfood.clients.application.DeleteClientRequest
import es.urjc.realfood.clients.application.DeleteClientTest
import es.urjc.realfood.clients.domain.exception.EntityNotFoundException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

class DeleteClientUnitaryTest : DeleteClientTest() {

    @Test
    fun `given valid id when delete user then ok`() {
        val client = validClient()

        `when`(clientRepository.findById(validClientId()))
            .thenReturn(client)

        deleteClient(validDeleteClientRequest())

        verify(clientRepository, atLeastOnce()).delete(client)
    }

    @Test
    fun `given unknown id when delete user then return illegal argument exception`() {
        `when`(clientRepository.findById(validClientId()))
            .thenReturn(null)

        val exc = assertThrows(EntityNotFoundException::class.java) {
            deleteClient(validDeleteClientRequest())
        }

        assertEquals("Client not found", exc.message)
    }

    @Test
    fun `given invalid client id when delete user then return illegal arg exception`() {
        val exc = assertThrows(IllegalArgumentException::class.java) {
            deleteClient(DeleteClientRequest("INVALID-ID"))
        }

        assertTrue(exc.message!!.contains("Invalid UUID"))
    }

}