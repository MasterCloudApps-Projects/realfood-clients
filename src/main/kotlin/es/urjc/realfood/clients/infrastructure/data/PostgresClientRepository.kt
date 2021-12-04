package es.urjc.realfood.clients.infrastructure.data

import es.urjc.realfood.clients.domain.Client
import es.urjc.realfood.clients.domain.ClientId
import es.urjc.realfood.clients.domain.Email
import es.urjc.realfood.clients.domain.Password
import es.urjc.realfood.clients.domain.repository.ClientRepository
import org.springframework.stereotype.Component

@Component
class PostgresClientRepository(private val jpaRepository: ClientJpaRepository) : ClientRepository {

    override fun findAll(): List<Client> = jpaRepository.findAll()

    override fun findById(id: ClientId): Client? = jpaRepository.findById(id).orElse(null)

    override fun save(client: Client): Client = jpaRepository.save(client)

    override fun delete(client: Client) = jpaRepository.delete(client)

    override fun findByEmailAndPassword(email: Email, password: Password) =
        jpaRepository.findByEmailAndPassword(email, password)

}