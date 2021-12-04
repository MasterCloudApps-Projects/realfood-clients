package es.urjc.realfood.clients.application

import es.urjc.realfood.clients.domain.repository.ClientRepository
import es.urjc.realfood.clients.domain.services.JWTService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class LoginClient(
    private val clientRepository: ClientRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
    private val jwtService: JWTService
) {

    operator fun invoke(loginClientRequest: LoginClientRequest): LoginClientResponse {
        TODO("Not implemented jet")
    }

}

data class LoginClientRequest(
    val email: String,
    val password: String
)

data class LoginClientResponse(
    val token: String,
    val clientId: String
)