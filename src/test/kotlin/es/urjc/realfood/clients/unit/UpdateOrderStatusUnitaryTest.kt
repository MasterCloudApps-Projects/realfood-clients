package es.urjc.realfood.clients.unit

import es.urjc.realfood.clients.application.UpdateOrderStatus
import es.urjc.realfood.clients.application.UpdateOrderStatusTest
import es.urjc.realfood.clients.domain.ClientObjectProvider.Companion.validClientId
import es.urjc.realfood.clients.domain.OrderObjectProvider.Companion.inProgressStatus
import es.urjc.realfood.clients.domain.OrderObjectProvider.Companion.pendingStatus
import es.urjc.realfood.clients.domain.OrderObjectProvider.Companion.validOrder
import es.urjc.realfood.clients.domain.OrderObjectProvider.Companion.validOrderId
import es.urjc.realfood.clients.domain.exception.EntityNotFoundException
import es.urjc.realfood.clients.domain.repository.OrderRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    classes = [
        OrderRepository::class
    ]
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UpdateOrderStatusUnitaryTest : UpdateOrderStatusTest() {

    lateinit var orderRepository: OrderRepository
    lateinit var updateOrderStatus: UpdateOrderStatus

    @BeforeAll
    fun init() {
        orderRepository = mock(OrderRepository::class.java)
        updateOrderStatus = UpdateOrderStatus(
            orderRepository = orderRepository
        )
    }

    @Test
    fun `given valid request when update order status then return valid order status and change status`() {
        val order = validOrder()

        assertEquals(pendingStatus(), order.status)

        `when`(orderRepository.findByIdAndClientId(validOrderId(), validClientId()))
            .thenReturn(order)

        updateOrderStatus(validUpdateOrderStatusRequest())

        assertEquals(inProgressStatus(), order.status)
    }

    @Test
    fun `given non existent order id request when update order status then return entity not found exception`() {
        `when`(orderRepository.findByIdAndClientId(validOrderId(), validClientId()))
            .thenReturn(null)

        val exc = assertThrows(EntityNotFoundException::class.java) {
            updateOrderStatus(validUpdateOrderStatusRequest())
        }

        assertEquals("Order not found", exc.message)
    }

}