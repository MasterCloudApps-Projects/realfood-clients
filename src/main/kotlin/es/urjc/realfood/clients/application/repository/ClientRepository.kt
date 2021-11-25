package es.urjc.realfood.clients.application.repository

import es.urjc.realfood.clients.domain.Client
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ClientRepository : JpaRepository<Client, String> {
}