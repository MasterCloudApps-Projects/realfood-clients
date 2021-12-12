package es.urjc.realfood.clients.unit

import es.urjc.realfood.clients.application.FindByIdClientRequest
import es.urjc.realfood.clients.application.FindByIdClientTest
import es.urjc.realfood.clients.domain.exception.EntityNotFoundException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`

class FindByIdClientUnitaryTest : FindByIdClientTest() {

    @Test
    fun `given valid id when ask for user then return user`() {
        `when`(clientRepository.findById(validClientId()))
            .thenReturn(validClient())

        val client = findByIdClient(validFindByIdClientRequest())

        assertEquals("Cristofer", client.name)
    }

    @Test
    fun `given unknown id when ask for user then return illegal argument exception`() {
        `when`(clientRepository.findById(validClientId()))
            .thenReturn(null)

        val exc = assertThrows(EntityNotFoundException::class.java) {
            findByIdClient(validFindByIdClientRequest())
        }

        assertEquals("Client not found", exc.message)
    }

    @Test
    fun `given invalid id when ask for user then return illegal argument exception`() {
        val exc = assertThrows(IllegalArgumentException::class.java) {
            findByIdClient(FindByIdClientRequest("INVALID-ID"))
        }

        Assertions.assertTrue(exc.message!!.contains("Invalid UUID"))
    }

}