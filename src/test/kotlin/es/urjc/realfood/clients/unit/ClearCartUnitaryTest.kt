package es.urjc.realfood.clients.unit

import es.urjc.realfood.clients.application.ClearCart
import es.urjc.realfood.clients.application.ClearCartTest
import es.urjc.realfood.clients.domain.CartObjectProvider.Companion.validCart
import es.urjc.realfood.clients.domain.ClientObjectProvider.Companion.validClientId
import es.urjc.realfood.clients.domain.exception.EntityNotFoundException
import es.urjc.realfood.clients.domain.repository.CartRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    classes = [
        CartRepository::class
    ]
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ClearCartUnitaryTest : ClearCartTest() {

    lateinit var cartRepository: CartRepository
    lateinit var clearCart: ClearCart

    @BeforeAll
    fun init() {
        cartRepository = mock(CartRepository::class.java)
        clearCart = ClearCart(cartRepository)
    }

    @Test
    fun `given valid id when clear user cart then clear cart`() {
        `when`(cartRepository.findByClientId(validClientId()))
            .thenReturn(validCart())

        val cart = validCart()
        cart.items.clear()

        clearCart(validClearCartRequest())

        verify(cartRepository, atLeastOnce()).save(cart)
    }

    @Test
    fun `given invalid id when clear user cart then return illegal argument exception`() {
        val exc = assertThrows(IllegalArgumentException::class.java) {
            clearCart(invalidClearCartRequest())
        }

        assertTrue(exc.message!!.contains("Invalid UUID"))
    }

    @Test
    fun `given valid id but nonexistent when clear user cart then return entity not found exception`() {
        `when`(cartRepository.findByClientId(validClientId()))
            .thenReturn(null)

        val exc = assertThrows(EntityNotFoundException::class.java) {
            clearCart(validClearCartRequest())
        }

        assertEquals("Cart not found", exc.message)
    }

}