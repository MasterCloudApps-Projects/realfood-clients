package es.urjc.realfood.clients.unit

import es.urjc.realfood.clients.application.CheckoutCartTest
import es.urjc.realfood.clients.domain.exception.EntityNotFoundException
import es.urjc.realfood.clients.domain.exception.ProductException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`

class CheckoutCartUnitaryTest : CheckoutCartTest() {

    @Test
    fun `given valid request when checkout user cart then return order id`() {
        `when`(cartRepository.findByClientId(validClientId()))
            .thenReturn(validCart())
        `when`(checkoutCartService(validCheckoutServiceRequest()))
            .thenReturn(validCheckoutServiceResponse())

        val response = checkoutCart(validCheckoutCartRequest())

        assertEquals(validOrderId().toString(), response.orderId)
    }

    @Test
    fun `given valid request and error status from external checkout when checkout user cart then return product exception`() {
        `when`(cartRepository.findByClientId(validClientId()))
            .thenReturn(validCart())
        `when`(checkoutCartService(validCheckoutServiceRequest()))
            .thenReturn(invalidCheckoutServiceResponse())

        val exc = Assertions.assertThrows(ProductException::class.java) {
            checkoutCart(validCheckoutCartRequest())
        }

        assertEquals("Error from Restaurants API", exc.message)
    }

    @Test
    fun `given invalid client id when checkout user cart then return illegal argument exception`() {
        val exc = Assertions.assertThrows(IllegalArgumentException::class.java) {
            checkoutCart(invalidCheckoutCartRequest())
        }

        Assertions.assertTrue(exc.message!!.contains("Invalid UUID"))
    }

    @Test
    fun `given valid request but nonexistent user when checkout user cart then return entity not found exception`() {
        `when`(cartRepository.findByClientId(validClientId()))
            .thenReturn(null)

        val exc = Assertions.assertThrows(EntityNotFoundException::class.java) {
            checkoutCart(validCheckoutCartRequest())
        }

        assertEquals("Cart not found", exc.message)
    }

    @Test
    fun `given valid request but empty cart user when checkout user cart then return illegal argument exception`() {
        `when`(cartRepository.findByClientId(validClientId()))
            .thenReturn(invalidCart())

        val exc = Assertions.assertThrows(IllegalArgumentException::class.java) {
            checkoutCart(validCheckoutCartRequest())
        }

        assertEquals("Empty cart!", exc.message)
    }

}