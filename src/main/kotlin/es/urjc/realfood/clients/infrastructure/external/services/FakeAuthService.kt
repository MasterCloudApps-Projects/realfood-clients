package es.urjc.realfood.clients.infrastructure.external.services

import es.urjc.realfood.clients.domain.services.AuthService
import es.urjc.realfood.clients.domain.services.RegisterRequest
import es.urjc.realfood.clients.domain.services.RegisterResponse
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*

@Component
class FakeAuthService(
    @Value("\${jwt.secret}") private val tokenSecret: String
) : AuthService {

    override fun invoke(registerRequest: RegisterRequest): RegisterResponse {
        val userId = UUID.nameUUIDFromBytes(registerRequest.email.toByteArray()).toString()
        return RegisterResponse(
            token = generateNewToken(registerRequest, userId),
            userId = userId
        )
    }
    //RFC 7519

    private fun generateNewToken(registerRequest: RegisterRequest, userId: String): String {
        return Jwts.builder()
            .setIssuedAt(Date.from(Instant.now()))
            .setSubject(userId)
            .setIssuer("realfood-auth")
            .setExpiration(Date.from(Instant.now().plusSeconds(60)))
            .addClaims(mapOf("role" to registerRequest.role))
            .signWith(
                SignatureAlgorithm.HS256,
                Base64.getEncoder().encodeToString(tokenSecret.encodeToByteArray()).toString()
            )
            .compact()
    }

}