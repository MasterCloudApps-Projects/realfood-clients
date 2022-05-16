package es.urjc.realfood.clients

import es.urjc.realfood.clients.domain.*
import es.urjc.realfood.clients.domain.repository.CartRepository
import es.urjc.realfood.clients.domain.repository.ClientRepository
import org.springframework.context.annotation.Profile
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import java.util.*
import javax.annotation.PostConstruct

@Component
@Profile("local")
class DatabaseInitializer(
    private val clientRepository: ClientRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
    private val cartRepository: CartRepository
) {

    @PostConstruct
    fun setUp() {
        val cristofer = clientRepository.save(
            Client(
                id = ClientId(UUID.nameUUIDFromBytes("cristofer@cristofer.es".toByteArray()).toString()),
                name = Name("Cristofer"),
                lastName = LastName("Lopez"),
                email = Email("cristofer@cristofer.es"),
                password = Password(bCryptPasswordEncoder.encode("1234"))
            )
        )

        val juan = clientRepository.save(
            Client(
                id = ClientId(UUID.nameUUIDFromBytes("juan@juan.es".toByteArray()).toString()),
                name = Name("juan"),
                lastName = LastName("Avila"),
                email = Email("juan@juan.es"),
                password = Password(bCryptPasswordEncoder.encode("1234"))
            )
        )

        val cristoferCart = Cart(
            id = CartId(UUID.nameUUIDFromBytes("cristofer".toByteArray()).toString()),
            client = cristofer
        )

        val cartItem = CartItem(
            itemId = UUID.nameUUIDFromBytes("hamburguesa".toByteArray()).toString(),
            quantity = 2
        )

        cristoferCart.items[cartItem.itemId] = cartItem

        cartRepository.save(cristoferCart)

        cartRepository.save(
            Cart(
                id = CartId(UUID.nameUUIDFromBytes("juan".toByteArray()).toString()),
                client = juan
            )
        )
    }

}