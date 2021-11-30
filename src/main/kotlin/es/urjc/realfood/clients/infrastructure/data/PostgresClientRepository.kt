package es.urjc.realfood.clients.infrastructure.data

import es.urjc.realfood.clients.domain.Client
import es.urjc.realfood.clients.domain.ClientId
import es.urjc.realfood.clients.domain.repository.ClientRepository
import org.springframework.stereotype.Component

@Component
class PostgresClientRepository(private val jpaRepository: ClientJpaRepository) : ClientRepository {

    override fun findAll(): List<Client> = jpaRepository.findAll()

    override fun findById(id: String): Client? = jpaRepository.findById(ClientId(id)).orElse(null)

    override fun save(client: Client): Client = jpaRepository.save(client)

    override fun delete(client: Client) = jpaRepository.delete(client)

}