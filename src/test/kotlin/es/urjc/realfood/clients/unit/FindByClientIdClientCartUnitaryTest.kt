package es.urjc.realfood.clients.unit

import es.urjc.realfood.clients.application.FindByClientIdClientCartTest
import es.urjc.realfood.clients.domain.exception.EntityNotFoundException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import java.lang.IllegalArgumentException

class FindByClientIdClientCartUnitaryTest : FindByClientIdClientCartTest() {

    @Test
    fun `given valid id when find cart by client id then return cart`() {
        `when`(cartRepository.findByClientId(validClientId()))
            .thenReturn(validCart())

        val cart = findByClientIdCart(validFindByClientIdCartRequest())

        assertEquals(validCartId().toString(), cart.cartId)
    }

    @Test
    fun `given invalid id when find cart by client id then return illegal argument exception`() {
        val exc = Assertions.assertThrows(IllegalArgumentException::class.java) {
            findByClientIdCart(invalidFindByClientIdCartRequest())
        }

        Assertions.assertTrue(exc.message!!.contains("Invalid UUID"))
    }

    @Test
    fun `given valid id but nonexistent when find cart by client id then return entity not found exception`() {
        `when`(cartRepository.findByClientId(validClientId()))
            .thenReturn(null)

        val exc = Assertions.assertThrows(EntityNotFoundException::class.java) {
            findByClientIdCart(validFindByClientIdCartRequest())
        }

        assertEquals("Cart not found", exc.message)
    }

}