package es.urjc.realfood.clients.unit

import es.urjc.realfood.clients.application.CheckoutCart
import es.urjc.realfood.clients.application.CheckoutCartTest
import es.urjc.realfood.clients.domain.CartObjectProvider.Companion.emptyCart
import es.urjc.realfood.clients.domain.CartObjectProvider.Companion.validCart
import es.urjc.realfood.clients.domain.ClientObjectProvider.Companion.validClientId
import es.urjc.realfood.clients.domain.ClientObjectProvider.Companion.validJwt
import es.urjc.realfood.clients.domain.OrderObjectProvider.Companion.validOrder
import es.urjc.realfood.clients.domain.OrderObjectProvider.Companion.validOrderId
import es.urjc.realfood.clients.domain.OrderObjectProvider.Companion.validPaymentEvent
import es.urjc.realfood.clients.domain.exception.EntityNotFoundException
import es.urjc.realfood.clients.domain.repository.CartRepository
import es.urjc.realfood.clients.domain.repository.OrderRepository
import es.urjc.realfood.clients.domain.services.CheckoutCartService
import es.urjc.realfood.clients.domain.services.PaymentEventPublisher
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    classes = [
        CartRepository::class,
        OrderRepository::class
    ]
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CheckoutCartUnitaryTest : CheckoutCartTest() {

    lateinit var cartRepository: CartRepository
    lateinit var orderRepository: OrderRepository
    lateinit var checkoutCartService: CheckoutCartService
    lateinit var paymentEventPublisher: PaymentEventPublisher
    lateinit var checkoutCart: CheckoutCart

    @BeforeAll
    fun init() {
        cartRepository = mock(CartRepository::class.java)
        orderRepository = mock(OrderRepository::class.java)
        checkoutCartService = mock(CheckoutCartService::class.java)
        paymentEventPublisher = mock(PaymentEventPublisher::class.java)
        checkoutCart = CheckoutCart(
            cartRepository = cartRepository,
            orderRepository = orderRepository,
            checkoutCartService = checkoutCartService,
            paymentEventPublisher = paymentEventPublisher
        )
    }

    @Test
    fun `given valid request when checkout user cart then return order id`() {
        `when`(cartRepository.findByClientId(validClientId()))
            .thenReturn(validCart())

        `when`(checkoutCartService(validCheckoutServiceRequest(), validJwt()))
            .thenReturn(validCheckoutServiceResponse())

        val response = checkoutCart(validCheckoutCartRequest())

        verify(orderRepository, atLeastOnce()).save(validOrder())
        verify(paymentEventPublisher, atLeastOnce()).invoke(validPaymentEvent())
        assertEquals(validOrderId().toString(), response.orderId)
    }

    @Test
    fun `given invalid client id when checkout user cart then return illegal argument exception`() {
        val exc = assertThrows(IllegalArgumentException::class.java) {
            checkoutCart(invalidCheckoutCartRequest())
        }

        assertTrue(exc.message!!.contains("Invalid UUID"))
    }

    @Test
    fun `given valid request but nonexistent user when checkout user cart then return entity not found exception`() {
        `when`(cartRepository.findByClientId(validClientId()))
            .thenReturn(null)

        val exc = assertThrows(EntityNotFoundException::class.java) {
            checkoutCart(validCheckoutCartRequest())
        }

        assertEquals("Cart not found", exc.message)
    }

    @Test
    fun `given valid request but empty cart user when checkout user cart then return illegal argument exception`() {
        `when`(cartRepository.findByClientId(validClientId()))
            .thenReturn(emptyCart())

        val exc = assertThrows(IllegalArgumentException::class.java) {
            checkoutCart(validCheckoutCartRequest())
        }

        assertEquals("Empty cart!", exc.message)
    }

}