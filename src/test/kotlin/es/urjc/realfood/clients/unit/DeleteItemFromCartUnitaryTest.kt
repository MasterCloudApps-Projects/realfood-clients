package es.urjc.realfood.clients.unit

import es.urjc.realfood.clients.application.DeleteItemFromCart
import es.urjc.realfood.clients.application.DeleteItemFromCartTest
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
class DeleteItemFromCartUnitaryTest : DeleteItemFromCartTest() {

    lateinit var cartRepository: CartRepository
    lateinit var deleteItemFromCart: DeleteItemFromCart

    @BeforeAll
    fun init() {
        cartRepository = mock(CartRepository::class.java)
        deleteItemFromCart = DeleteItemFromCart(
            cartRepository = cartRepository
        )
    }

    @Test
    fun `given valid request when remove item from user cart then remove item`() {
        `when`(cartRepository.findByClientId(validClientId()))
            .thenReturn(validCart())

        val cart = validCart()

        deleteItemFromCart(validDeleteItemFromCartRequest())

        verify(cartRepository, atLeastOnce()).save(cart)
    }

    @Test
    fun `given invalid client id when remove item from user cart then return illegal argument exception`() {
        val exc = assertThrows(IllegalArgumentException::class.java) {
            deleteItemFromCart(invalidDeleteItemFromCartRequest())
        }

        assertTrue(exc.message!!.contains("Invalid UUID"))
    }

    @Test
    fun `given valid request but nonexistent user when remove item from user cart then return entity not found exception`() {
        `when`(cartRepository.findByClientId(validClientId()))
            .thenReturn(null)

        val exc = assertThrows(EntityNotFoundException::class.java) {
            deleteItemFromCart(validDeleteItemFromCartRequest())
        }

        assertEquals("Cart not found", exc.message)
    }

}