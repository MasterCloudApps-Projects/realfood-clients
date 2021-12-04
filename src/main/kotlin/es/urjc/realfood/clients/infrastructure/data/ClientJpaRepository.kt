package es.urjc.realfood.clients.infrastructure.data

import es.urjc.realfood.clients.domain.Client
import es.urjc.realfood.clients.domain.ClientId
import es.urjc.realfood.clients.domain.Email
import es.urjc.realfood.clients.domain.Password
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ClientJpaRepository : JpaRepository<Client, ClientId>{

    fun findByEmailAndPassword(email: Email, password: Password): Optional<Client>

}