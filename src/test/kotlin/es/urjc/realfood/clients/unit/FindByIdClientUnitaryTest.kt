package es.urjc.realfood.clients.unit

import es.urjc.realfood.clients.application.FindByIdClient
import es.urjc.realfood.clients.application.FindByIdClientTest
import es.urjc.realfood.clients.domain.ClientObjectProvider.Companion.validClient
import es.urjc.realfood.clients.domain.ClientObjectProvider.Companion.validClientId
import es.urjc.realfood.clients.domain.exception.EntityNotFoundException
import es.urjc.realfood.clients.domain.repository.ClientRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    classes = [
        ClientRepository::class
    ]
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FindByIdClientUnitaryTest : FindByIdClientTest() {

    lateinit var clientRepository: ClientRepository
    lateinit var findByIdClient: FindByIdClient

    @BeforeAll
    fun init() {
        clientRepository = Mockito.mock(ClientRepository::class.java)

        findByIdClient = FindByIdClient(
            clientRepository = clientRepository
        )
    }

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
            findByIdClient(invalidFindByIdClientRequest())
        }

        assertTrue(exc.message!!.contains("Invalid UUID"))
    }

}