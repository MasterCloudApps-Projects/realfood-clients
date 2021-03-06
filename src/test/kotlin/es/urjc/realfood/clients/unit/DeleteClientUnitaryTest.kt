package es.urjc.realfood.clients.unit

import es.urjc.realfood.clients.application.DeleteClient
import es.urjc.realfood.clients.application.DeleteClientRequest
import es.urjc.realfood.clients.application.DeleteClientTest
import es.urjc.realfood.clients.domain.ClientObjectProvider.Companion.validClient
import es.urjc.realfood.clients.domain.ClientObjectProvider.Companion.validClientId
import es.urjc.realfood.clients.domain.ClientObjectProvider.Companion.validDeleteClientEvent
import es.urjc.realfood.clients.domain.exception.EntityNotFoundException
import es.urjc.realfood.clients.domain.repository.CartRepository
import es.urjc.realfood.clients.domain.repository.ClientRepository
import es.urjc.realfood.clients.domain.services.DeleteClientEventPublisher
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    classes = [
        ClientRepository::class,
        CartRepository::class
    ]
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DeleteClientUnitaryTest : DeleteClientTest() {

    lateinit var clientRepository: ClientRepository
    lateinit var cartRepository: CartRepository
    lateinit var deleteClientEventPublisher: DeleteClientEventPublisher
    lateinit var deleteClient: DeleteClient

    @BeforeAll
    fun init() {
        clientRepository = mock(ClientRepository::class.java)
        cartRepository = mock(CartRepository::class.java)
        deleteClientEventPublisher = mock(DeleteClientEventPublisher::class.java)

        deleteClient = DeleteClient(
            clientRepository = clientRepository,
            cartRepository = cartRepository,
            deleteClientEventPublisher = deleteClientEventPublisher
        )
    }

    @Test
    fun `given valid id when delete user then ok`() {
        val client = validClient()

        `when`(clientRepository.findById(validClientId()))
            .thenReturn(client)

        deleteClient(validDeleteClientRequest())

        verify(clientRepository, atLeastOnce()).delete(client)
        verify(deleteClientEventPublisher, atLeastOnce()).invoke(validDeleteClientEvent())
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