package es.urjc.realfood.clients.unit

import es.urjc.realfood.clients.application.DeleteItemFromCartTest
import es.urjc.realfood.clients.domain.exception.EntityNotFoundException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

class DeleteItemFromCartUnitaryTest : DeleteItemFromCartTest() {

    @Test
    fun `given valid request when remove item from user cart then remove item`() {
        `when`(cartRepository.findByClientId(validClientId()))
            .thenReturn(validCart())

        val cart = validCart()

        deleteItemFromCart(validDeleteItemFromCartRequest())

        verify(cartRepository, atLeastOnce()).save(cart)
    }

    @Test
    fun `given invalid client id when remove item from user cart then return illegal argument exception`() {
        val exc = Assertions.assertThrows(IllegalArgumentException::class.java) {
            deleteItemFromCart(invalidDeleteItemFromCartRequest())
        }

        Assertions.assertTrue(exc.message!!.contains("Invalid UUID"))
    }

    @Test
    fun `given valid request but nonexistent user when remove item from user cart then return entity not found exception`() {
        `when`(cartRepository.findByClientId(validClientId()))
            .thenReturn(null)

        val exc = Assertions.assertThrows(EntityNotFoundException::class.java) {
            deleteItemFromCart(validDeleteItemFromCartRequest())
        }

        assertEquals("Cart not found", exc.message)
    }

}