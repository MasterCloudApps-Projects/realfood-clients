package es.urjc.realfood.clients.unit

import es.urjc.realfood.clients.application.FindByIdClientTest
import es.urjc.realfood.clients.domain.exception.ClientNotFoundException
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

        val exc = assertThrows(ClientNotFoundException::class.java) {
            findByIdClient(validFindByIdClientRequest())
        }

        assertEquals("Client not found", exc.message)
    }

}