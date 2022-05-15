package es.urjc.realfood.clients.unit

import es.urjc.realfood.clients.application.FindByIdAndClientIdOrder
import es.urjc.realfood.clients.application.FindByIdAndClientIdOrderTest
import es.urjc.realfood.clients.domain.ClientObjectProvider.Companion.validClientId
import es.urjc.realfood.clients.domain.OrderObjectProvider.Companion.validOrder
import es.urjc.realfood.clients.domain.OrderObjectProvider.Companion.validOrderId
import es.urjc.realfood.clients.domain.exception.EntityNotFoundException
import es.urjc.realfood.clients.domain.repository.CartRepository
import es.urjc.realfood.clients.domain.repository.OrderRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    classes = [
        CartRepository::class,
        OrderRepository::class
    ]
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FindByIdAndClientIdOrderUnitaryTest : FindByIdAndClientIdOrderTest() {

    lateinit var orderRepository: OrderRepository
    lateinit var findByIdAndClientIdOrder: FindByIdAndClientIdOrder

    @BeforeAll
    fun init() {
        orderRepository = Mockito.mock(OrderRepository::class.java)
        findByIdAndClientIdOrder = FindByIdAndClientIdOrder(
            orderRepository = orderRepository,
        )
    }

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
        val exc = assertThrows(IllegalArgumentException::class.java) {
            findByIdAndClientIdOrder(invalidClientIdRequest())
        }

        assertTrue(exc.message!!.contains("Invalid UUID"))
    }

    @Test
    fun `given invalid order id when get user order then return illegal argument exception`() {
        val exc = assertThrows(IllegalArgumentException::class.java) {
            findByIdAndClientIdOrder(invalidOrderIdRequest())
        }

        assertTrue(exc.message!!.contains("Invalid UUID"))
    }

    @Test
    fun `given valid request but nonexistent order or user id when get user order then return not found`() {
        `when`(orderRepository.findByIdAndClientId(validOrderId(), validClientId()))
            .thenReturn(null)

        val exc = assertThrows(EntityNotFoundException::class.java) {
            findByIdAndClientIdOrder(validRequest())
        }

        assertEquals("Order not found", exc.message)
    }

}