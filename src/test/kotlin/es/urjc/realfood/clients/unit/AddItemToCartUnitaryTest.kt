package es.urjc.realfood.clients.unit

import es.urjc.realfood.clients.application.AddItemToCart
import es.urjc.realfood.clients.application.AddItemToCartTest
import es.urjc.realfood.clients.domain.CartObjectProvider.Companion.emptyCart
import es.urjc.realfood.clients.domain.CartObjectProvider.Companion.validCart
import es.urjc.realfood.clients.domain.CartObjectProvider.Companion.validItemId
import es.urjc.realfood.clients.domain.ClientObjectProvider.Companion.validClientId
import es.urjc.realfood.clients.domain.exception.EntityNotFoundException
import es.urjc.realfood.clients.domain.repository.CartRepository
import es.urjc.realfood.clients.domain.services.FindByIdProductService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    classes = [
        CartRepository::class,
        FindByIdProductService::class
    ]
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AddItemToCartUnitaryTest : AddItemToCartTest() {

    lateinit var cartRepository: CartRepository
    lateinit var findByIdProductService: FindByIdProductService
    lateinit var addItemToCart: AddItemToCart

    @BeforeAll
    fun init() {
        cartRepository = mock(CartRepository::class.java)
        findByIdProductService = mock(FindByIdProductService::class.java)
        addItemToCart = AddItemToCart(
            cartRepository = cartRepository,
            findByIdProductService = findByIdProductService
        )
    }

    @Test
    fun `given valid request when add item to user cart then return added item`() {
        `when`(cartRepository.findByClientId(validClientId()))
            .thenReturn(emptyCart())

        `when`(findByIdProductService(validFindByIdProductServiceRequest()))
            .thenReturn(validFindByIdProductServiceResponse())

        val cart = emptyCart()

        val response = addItemToCart(validAddItemToCartRequest())

        verify(cartRepository, atLeastOnce()).save(cart)
        assertEquals(validItemId(), response.itemId)
        assertEquals(validAddItemToCartRequest().quantity, response.quantity)
    }

    @Test
    fun `given invalid client id when add item to user cart then return illegal argument exception`() {
        val exc = assertThrows(IllegalArgumentException::class.java) {
            addItemToCart(invalidIdAddItemToCartRequest())
        }

        assertTrue(exc.message!!.contains("Invalid UUID"))
    }

    @Test
    fun `given invalid quantity when add item to user cart then return illegal argument exception`() {
        val exc = assertThrows(IllegalArgumentException::class.java) {
            addItemToCart(invalidQuantityAddItemToCartRequest())
        }

        assertEquals("Quantity must be a integer positive value", exc.message)
    }

    @Test
    fun `given valid request but nonexistent user when add item to user cart then return entity not found exception`() {
        `when`(cartRepository.findByClientId(validClientId()))
            .thenReturn(null)

        val exc = assertThrows(EntityNotFoundException::class.java) {
            addItemToCart(validAddItemToCartRequest())
        }

        assertEquals("Cart not found", exc.message)
    }

    @Test
    fun `given valid request but nonexistent item when add item to user cart then return entity not found exception`() {
        `when`(cartRepository.findByClientId(validClientId()))
            .thenReturn(validCart())

        `when`(findByIdProductService(validFindByIdProductServiceRequest()))
            .thenReturn(invalidFindByIdProductServiceResponse404())

        val exc = assertThrows(EntityNotFoundException::class.java) {
            addItemToCart(validAddItemToCartRequest())
        }

        assertEquals("Product not found", exc.message)
    }

}