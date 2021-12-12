package es.urjc.realfood.clients.unit

import es.urjc.realfood.clients.application.AddItemToCartTest
import es.urjc.realfood.clients.domain.exception.EntityNotFoundException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

class AddItemToCartUnitaryTest : AddItemToCartTest() {

    @Test
    fun `given valid request when add item to user cart then return added item`() {
        `when`(cartRepository.findByClientId(validClientId()))
            .thenReturn(validCart())
        `when`(findByIdProductService(validFindByIdProductServiceRequest()))
            .thenReturn(validFindByIdProductServiceResponse())

        val cart = validCart()

        val response = addItemToCart(validAddItemToCartRequest())

        verify(cartRepository, atLeastOnce()).save(cart)
        assertEquals(validItemId(), response.itemId)
        assertEquals(validAddItemToCartRequest().quantity, response.quantity)
    }

    @Test
    fun `given invalid client id when add item to user cart then return illegal argument exception`() {
        val exc = Assertions.assertThrows(IllegalArgumentException::class.java) {
            addItemToCart(invalidIdAddItemToCartRequest())
        }

        Assertions.assertTrue(exc.message!!.contains("Invalid UUID"))
    }

    @Test
    fun `given invalid quantity when add item to user cart then return illegal argument exception`() {
        val exc = Assertions.assertThrows(IllegalArgumentException::class.java) {
            addItemToCart(invalidQuantityAddItemToCartRequest())
        }

        assertEquals("Quantity must be a integer positive value", exc.message)
    }

    @Test
    fun `given valid request but nonexistent user when add item to user cart then return entity not found exception`() {
        `when`(cartRepository.findByClientId(validClientId()))
            .thenReturn(null)

        val exc = Assertions.assertThrows(EntityNotFoundException::class.java) {
            addItemToCart(validAddItemToCartRequest())
        }

        assertEquals("Cart not found", exc.message)
    }

    @Test
    fun `given valid request but nonexistent item when add item to user cart then return entity not found exception`() {
        `when`(cartRepository.findByClientId(validClientId()))
            .thenReturn(validCart())
        `when`(findByIdProductService(validFindByIdProductServiceRequest()))
            .thenReturn(invalidFindByIdProductServiceResponse())

        val exc = Assertions.assertThrows(EntityNotFoundException::class.java) {
            addItemToCart(validAddItemToCartRequest())
        }

        assertEquals("Product not found", exc.message)
    }

}