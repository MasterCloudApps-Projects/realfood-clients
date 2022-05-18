package es.urjc.realfood.clients.unit

import es.urjc.realfood.clients.application.PrepareOrder
import es.urjc.realfood.clients.application.PrepareOrderTest
import es.urjc.realfood.clients.domain.OrderObjectProvider.Companion.validOrder
import es.urjc.realfood.clients.domain.OrderObjectProvider.Companion.validOrderId
import es.urjc.realfood.clients.domain.OrderObjectProvider.Companion.validPreparationEvent
import es.urjc.realfood.clients.domain.exception.EntityNotFoundException
import es.urjc.realfood.clients.domain.repository.OrderRepository
import es.urjc.realfood.clients.domain.services.PreparationEventPublisher
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    classes = [
        OrderRepository::class,
        PreparationEventPublisher::class
    ]
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PrepareOrderUnitaryTest : PrepareOrderTest() {

    lateinit var orderRepository: OrderRepository
    lateinit var preparationEventPublisher: PreparationEventPublisher
    lateinit var prepareOrder: PrepareOrder

    @BeforeAll
    fun init() {
        orderRepository = mock(OrderRepository::class.java)
        preparationEventPublisher = mock(PreparationEventPublisher::class.java)

        prepareOrder = PrepareOrder(
            orderRepository = orderRepository,
            preparationEventPublisher = preparationEventPublisher
        )
    }

    @Test
    fun `given valid request when prepare order then send message to preparation publisher`() {
        `when`(orderRepository.findById(validOrderId())).thenReturn(validOrder())

        prepareOrder(validPrepareOrderRequest())

        verify(preparationEventPublisher, atLeastOnce()).invoke(validPreparationEvent())
    }

    @Test
    fun `given invalid request when prepare order then throw illegal state exception`() {
        val exc = assertThrows(IllegalArgumentException::class.java) {
            prepareOrder(invalidPrepareOrderRequest())
        }

        assertTrue(exc.message!!.contains("Invalid UUID"))
    }

    @Test
    fun `given non existent order id when prepare order then throw entity not found exception`() {
        `when`(orderRepository.findById(validOrderId())).thenReturn(null)

        val exc = assertThrows(EntityNotFoundException::class.java) {
            prepareOrder(validPrepareOrderRequest())
        }

        assertEquals("Order not found", exc.message!!)
    }


}