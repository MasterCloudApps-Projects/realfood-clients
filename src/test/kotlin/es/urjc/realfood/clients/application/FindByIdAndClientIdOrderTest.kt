package es.urjc.realfood.clients.application

import es.urjc.realfood.clients.domain.*
import es.urjc.realfood.clients.domain.repository.CartRepository
import es.urjc.realfood.clients.domain.repository.OrderRepository
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito.mock
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    classes = [
        CartRepository::class,
        OrderRepository::class
    ]
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FindByIdAndClientIdOrderTest {

    lateinit var orderRepository: OrderRepository
    lateinit var findByIdAndClientIdOrder: FindByIdAndClientIdOrder

    @BeforeAll
    fun init() {
        orderRepository = mock(OrderRepository::class.java)
        findByIdAndClientIdOrder = FindByIdAndClientIdOrder(
            orderRepository = orderRepository,
        )
    }

    protected fun validRequest(): FindByIdAndClientIdOrderRequest {
        return FindByIdAndClientIdOrderRequest(
            clientId = validClientId().toString(),
            orderId = validOrderId().toString()
        )
    }

    protected fun invalidOrderIdRequest(): FindByIdAndClientIdOrderRequest {
        return FindByIdAndClientIdOrderRequest(
            clientId = "INVALID",
            orderId = validOrderId().toString()
        )
    }

    protected fun invalidClientIdRequest(): FindByIdAndClientIdOrderRequest {
        return FindByIdAndClientIdOrderRequest(
            clientId = validClientId().toString(),
            orderId = "INVALID"
        )
    }

    protected fun validOrder(): Order {
        return Order(
            id = validOrderId(),
            status = Status.PENDING,
            client = validClient()
        )
    }

    protected fun validClient(): Client {
        return Client(
            id = validClientId(),
            name = Name("Cristofer"),
            lastName = LastName("Lopez"),
            email = Email("cristofer@cristofer.es"),
            password = Password("1234")
        )
    }

    protected fun validCartId(): CartId = CartId("89a135b8-98dc-4e57-a22f-b5f99c6b1a99")

    protected fun validClientId(): ClientId = ClientId("89a135b8-98dc-4e57-a22f-b5f99c6b1a00")

    protected fun validOrderId(): OrderId = OrderId("89a135b8-98dc-4e57-a22f-b5f99c6b1a11")

}