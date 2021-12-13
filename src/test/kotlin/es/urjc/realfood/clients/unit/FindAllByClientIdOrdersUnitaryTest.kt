package es.urjc.realfood.clients.unit

import es.urjc.realfood.clients.application.FindAllByClientIdOrdersTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`

class FindAllByClientIdOrdersUnitaryTest : FindAllByClientIdOrdersTest() {

    @Test
    fun `given valid request when get user orders then return order list`() {
        `when`(orderRepository.findAllByClientId(validClientId()))
            .thenReturn(validOrders())

        val response = findAllByClientIdOrders(validFindAllByClientIdOrdersRequest())

        assertEquals(validClientId().toString(), response.clientId)
        assertEquals(1, response.orders.size)
    }

    @Test
    fun `given invalid client id when get user orders then return illegal argument exception`() {
        val exc = Assertions.assertThrows(IllegalArgumentException::class.java) {
            findAllByClientIdOrders(invalidFindAllByClientIdOrdersRequest())
        }

        Assertions.assertTrue(exc.message!!.contains("Invalid UUID"))
    }

}