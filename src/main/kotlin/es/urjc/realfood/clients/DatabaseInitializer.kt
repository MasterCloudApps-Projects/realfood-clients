package es.urjc.realfood.clients

import es.urjc.realfood.clients.domain.Client
import es.urjc.realfood.clients.domain.ClientId
import es.urjc.realfood.clients.domain.LastName
import es.urjc.realfood.clients.domain.Name
import es.urjc.realfood.clients.domain.repository.ClientRepository
import org.springframework.stereotype.Component

@Component
class DatabaseInitializer(
    private val clientRepository: ClientRepository
) {

    init {
        clientRepository.save(
            Client(
                id = ClientId("22bac1ee-ebd9-45b7-b280-9023f353a285"),
                name = Name("Cristofer"),
                lastName = LastName("Lopez")
            )
        )
        clientRepository.save(
            Client(
                id = ClientId("89044589-62e9-4ad4-b44f-ba11bbec8a93"),
                name = Name("juan"),
                lastName = LastName("Avila")
            )
        )
    }

}