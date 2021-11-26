package es.urjc.realfood.clients

import es.urjc.realfood.clients.domain.Client
import es.urjc.realfood.clients.domain.Name
import es.urjc.realfood.clients.domain.repository.ClientRepository
import org.springframework.stereotype.Component

@Component
class DatabaseInitializer(
    private val clientRepository: ClientRepository
) {

    init {
        clientRepository.save(Client(Name("Cristofer"), Name("Lopez"), "22bac1ee-ebd9-45b7-b280-9023f353a285"))
        clientRepository.save(Client(Name("juan"), Name("Avila"), "89044589-62e9-4ad4-b44f-ba11bbec8a93"))
    }

}