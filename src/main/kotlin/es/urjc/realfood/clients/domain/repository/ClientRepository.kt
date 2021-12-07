package es.urjc.realfood.clients.domain.repository

import es.urjc.realfood.clients.domain.Client
import es.urjc.realfood.clients.domain.ClientId
import es.urjc.realfood.clients.domain.Email

interface ClientRepository {

    fun findAll(): List<Client>

    fun findById(id: ClientId): Client?

    fun save(client: Client): Client

    fun delete(client: Client)

    fun findByEmail(email: Email): Client?

}