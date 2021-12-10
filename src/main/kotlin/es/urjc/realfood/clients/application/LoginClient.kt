package es.urjc.realfood.clients.application

import es.urjc.realfood.clients.domain.Email
import es.urjc.realfood.clients.domain.exception.EntityNotFoundException
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

    operator fun invoke(request: LoginClientRequest): LoginClientResponse {
        val client = clientRepository
            .findByEmail(
                email = Email(request.email)
            ) ?: throw EntityNotFoundException("Client not found")

        if (!bCryptPasswordEncoder.matches(request.password, client.password.toString()))
            throw IllegalArgumentException("Invalid password")

        val userId = client.id.toString()

        return LoginClientResponse(
            token = jwtService.generateJwt(userId),
            clientId = userId
        )
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