package es.urjc.realfood.clients.infrastructure.data

import es.urjc.realfood.clients.domain.Client
import es.urjc.realfood.clients.domain.ClientId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ClientJpaRepository : JpaRepository<Client, ClientId>