package es.urjc.realfood.clients.domain.repository

import es.urjc.realfood.clients.domain.Client
import es.urjc.realfood.clients.domain.ClientId

interface ClientRepository {

    fun findAll(): List<Client>

    fun findById(id: ClientId): Client?

    fun save(client: Client): Client

    fun delete(client: Client)

}