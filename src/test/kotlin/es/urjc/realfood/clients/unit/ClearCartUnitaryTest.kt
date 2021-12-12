package es.urjc.realfood.clients.unit

import es.urjc.realfood.clients.application.ClearCartTest
import es.urjc.realfood.clients.domain.exception.EntityNotFoundException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

class ClearCartUnitaryTest : ClearCartTest() {

    @Test
    fun `given valid id when clear user cart then clear cart`() {
        `when`(cartRepository.findByClientId(validClientId()))
            .thenReturn(validCart())

        val cart = validCart()
        cart.items.clear()

        clearCart(validClearCartRequest())

        verify(cartRepository, atLeastOnce()).save(cart)
    }

    @Test
    fun `given invalid id when clear user cart then return illegal argument exception`() {
        val exc = Assertions.assertThrows(IllegalArgumentException::class.java) {
            clearCart(invalidClearCartRequest())
        }

        Assertions.assertTrue(exc.message!!.contains("Invalid UUID"))
    }

    @Test
    fun `given valid id but nonexistent when clear user cart then return entity not found exception`() {
        `when`(cartRepository.findByClientId(validClientId()))
            .thenReturn(null)

        val exc = Assertions.assertThrows(EntityNotFoundException::class.java) {
            clearCart(validClearCartRequest())
        }

        assertEquals("Cart not found", exc.message)
    }

}