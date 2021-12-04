package es.urjc.realfood.clients.domain.repository

import es.urjc.realfood.clients.domain.Client
import es.urjc.realfood.clients.domain.ClientId
import es.urjc.realfood.clients.domain.Email
import es.urjc.realfood.clients.domain.Password

interface ClientRepository {

    fun findAll(): List<Client>

    fun findById(id: ClientId): Client?

    fun save(client: Client): Client

    fun delete(client: Client)

    fun findByEmailAndPassword(email: Email, password: Password): Client?

}