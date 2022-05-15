package es.urjc.realfood.clients.unit

import es.urjc.realfood.clients.application.CheckoutCart
import es.urjc.realfood.clients.application.CheckoutCartTest
import es.urjc.realfood.clients.domain.CartObjectProvider.Companion.emptyCart
import es.urjc.realfood.clients.domain.CartObjectProvider.Companion.validCart
import es.urjc.realfood.clients.domain.ClientObjectProvider.Companion.validClientId
import es.urjc.realfood.clients.domain.OrderObjectProvider.Companion.validOrderId
import es.urjc.realfood.clients.domain.exception.EntityNotFoundException
import es.urjc.realfood.clients.domain.exception.ProductException
import es.urjc.realfood.clients.domain.repository.CartRepository
import es.urjc.realfood.clients.domain.repository.OrderRepository
import es.urjc.realfood.clients.domain.services.CheckoutCartService
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
class CheckoutCartUnitaryTest : CheckoutCartTest() {

    lateinit var cartRepository: CartRepository
    lateinit var orderRepository: OrderRepository
    lateinit var checkoutCartService: CheckoutCartService
    lateinit var checkoutCart: CheckoutCart

    @BeforeAll
    fun init() {
        cartRepository = Mockito.mock(CartRepository::class.java)
        orderRepository = Mockito.mock(OrderRepository::class.java)
        checkoutCartService = Mockito.mock(CheckoutCartService::class.java)
        checkoutCart = CheckoutCart(
            cartRepository = cartRepository,
            orderRepository = orderRepository,
            checkoutCartService = checkoutCartService
        )
    }

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

        val exc = assertThrows(ProductException::class.java) {
            checkoutCart(validCheckoutCartRequest())
        }

        assertEquals("Error from Restaurants API", exc.message)
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