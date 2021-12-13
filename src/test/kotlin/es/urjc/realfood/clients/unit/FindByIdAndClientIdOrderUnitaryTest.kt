package es.urjc.realfood.clients.unit

import es.urjc.realfood.clients.application.FindByIdAndClientIdOrderTest
import es.urjc.realfood.clients.domain.exception.EntityNotFoundException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`

class FindByIdAndClientIdOrderUnitaryTest : FindByIdAndClientIdOrderTest() {

    @Test
    fun `given valid request when get user order then return order`() {
        `when`(orderRepository.findByIdAndClientId(validOrderId(), validClientId()))
            .thenReturn(validOrder())

        val response = findByIdAndClientIdOrder(validRequest())

        assertEquals(validOrderId().toString(), response.orderId)
        assertEquals("PENDING", response.status)
    }

    @Test
    fun `given invalid client id when get user order then return illegal argument exception`() {
        val exc = Assertions.assertThrows(IllegalArgumentException::class.java) {
            findByIdAndClientIdOrder(invalidClientIdRequest())
        }

        Assertions.assertTrue(exc.message!!.contains("Invalid UUID"))
    }

    @Test
    fun `given invalid order id when get user order then return illegal argument exception`() {
        val exc = Assertions.assertThrows(IllegalArgumentException::class.java) {
            findByIdAndClientIdOrder(invalidOrderIdRequest())
        }

        Assertions.assertTrue(exc.message!!.contains("Invalid UUID"))
    }

    @Test
    fun `given valid request but nonexistent order or user id when get user order then return not found`() {
        `when`(orderRepository.findByIdAndClientId(validOrderId(), validClientId()))
            .thenReturn(null)

        val exc = Assertions.assertThrows(EntityNotFoundException::class.java) {
            findByIdAndClientIdOrder(validRequest())
        }

        assertEquals("Order not found", exc.message)
    }

}