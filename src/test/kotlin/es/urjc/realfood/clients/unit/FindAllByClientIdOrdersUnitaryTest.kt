package es.urjc.realfood.clients.unit

import es.urjc.realfood.clients.application.FindAllByClientIdOrders
import es.urjc.realfood.clients.application.FindAllByClientIdOrdersTest
import es.urjc.realfood.clients.domain.ClientObjectProvider.Companion.validClientId
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
class FindAllByClientIdOrdersUnitaryTest : FindAllByClientIdOrdersTest() {

    lateinit var orderRepository: OrderRepository
    lateinit var findAllByClientIdOrders: FindAllByClientIdOrders

    @BeforeAll
    fun init() {
        orderRepository = Mockito.mock(OrderRepository::class.java)
        findAllByClientIdOrders = FindAllByClientIdOrders(
            orderRepository = orderRepository,
        )
    }

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
        val exc = assertThrows(IllegalArgumentException::class.java) {
            findAllByClientIdOrders(invalidFindAllByClientIdOrdersRequest())
        }

        assertTrue(exc.message!!.contains("Invalid UUID"))
    }

}