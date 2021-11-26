package es.urjc.realfood.clients.domain.repository

import es.urjc.realfood.clients.domain.Client

interface ClientRepository {

    fun findAll(): List<Client>

    fun findById(id: String): Client?

    fun save(client: Client): Client

    fun delete(client: Client)

}