package es.urjc.realfood.clients.unit

import es.urjc.realfood.clients.application.SendOrder
import es.urjc.realfood.clients.application.SendOrderTest
import es.urjc.realfood.clients.domain.ClientObjectProvider.Companion.validClientId
import es.urjc.realfood.clients.domain.OrderObjectProvider.Companion.validOrder
import es.urjc.realfood.clients.domain.OrderObjectProvider.Companion.validOrderId
import es.urjc.realfood.clients.domain.OrderObjectProvider.Companion.validShipmentEvent
import es.urjc.realfood.clients.domain.exception.EntityNotFoundException
import es.urjc.realfood.clients.domain.repository.OrderRepository
import es.urjc.realfood.clients.domain.services.ShipmentEventPublisher
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    classes = [
        OrderRepository::class,
        ShipmentEventPublisher::class
    ]
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SendOrderUnitaryTest : SendOrderTest() {

    lateinit var orderRepository: OrderRepository
    lateinit var shipmentEventPublisher: ShipmentEventPublisher
    lateinit var sendOrder: SendOrder

    @BeforeAll
    fun init() {
        orderRepository = mock(OrderRepository::class.java)
        shipmentEventPublisher = mock(ShipmentEventPublisher::class.java)

        sendOrder = SendOrder(
            orderRepository = orderRepository,
            shipmentEventPublisher = shipmentEventPublisher
        )
    }

    @Test
    fun `given valid request when send order then send message to shipment publisher`() {
        `when`(orderRepository.findByIdAndClientId(validOrderId(), validClientId()))
            .thenReturn(validOrder())

        sendOrder(validSendOrderRequest())

        verify(shipmentEventPublisher, atLeastOnce()).invoke(validShipmentEvent())
    }

    @Test
    fun `given invalid request when send order then throw illegal state exception`() {
        val exc = assertThrows(IllegalArgumentException::class.java) {
            sendOrder(invalidSendOrderRequest())
        }

        assertTrue(exc.message!!.contains("Invalid UUID"))
    }

    @Test
    fun `given non existent order id when send order then throw entity not found exception`() {
        `when`(orderRepository.findByIdAndClientId(validOrderId(), validClientId()))
            .thenReturn(null)

        val exc = assertThrows(EntityNotFoundException::class.java) {
            sendOrder(validSendOrderRequest())
        }

        assertEquals("Order not found", exc.message!!)
    }


}