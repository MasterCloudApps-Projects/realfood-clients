package es.urjc.realfood.clients.unit

import es.urjc.realfood.clients.application.FindByClientIdCart
import es.urjc.realfood.clients.application.FindByClientIdClientCartTest
import es.urjc.realfood.clients.domain.CartObjectProvider.Companion.validCart
import es.urjc.realfood.clients.domain.CartObjectProvider.Companion.validCartId
import es.urjc.realfood.clients.domain.ClientObjectProvider.Companion.validClientId
import es.urjc.realfood.clients.domain.exception.EntityNotFoundException
import es.urjc.realfood.clients.domain.repository.CartRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    classes = [
        CartRepository::class
    ]
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FindByClientIdClientCartUnitaryTest : FindByClientIdClientCartTest() {

    lateinit var cartRepository: CartRepository
    lateinit var findByClientIdCart: FindByClientIdCart

    @BeforeAll
    fun init() {
        cartRepository = Mockito.mock(CartRepository::class.java)
        findByClientIdCart = FindByClientIdCart(cartRepository)
    }

    @Test
    fun `given valid id when find cart by client id then return cart`() {
        `when`(cartRepository.findByClientId(validClientId()))
            .thenReturn(validCart())

        val cart = findByClientIdCart(validFindByClientIdCartRequest())

        assertEquals(validCartId().toString(), cart.cartId)
    }

    @Test
    fun `given invalid id when find cart by client id then return illegal argument exception`() {
        val exc = assertThrows(IllegalArgumentException::class.java) {
            findByClientIdCart(invalidFindByClientIdCartRequest())
        }

        assertTrue(exc.message!!.contains("Invalid UUID"))
    }

    @Test
    fun `given valid id but nonexistent when find cart by client id then return entity not found exception`() {
        `when`(cartRepository.findByClientId(validClientId()))
            .thenReturn(null)

        val exc = assertThrows(EntityNotFoundException::class.java) {
            findByClientIdCart(validFindByClientIdCartRequest())
        }

        assertEquals("Cart not found", exc.message)
    }

}