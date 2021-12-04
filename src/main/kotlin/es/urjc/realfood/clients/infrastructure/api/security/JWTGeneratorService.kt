package es.urjc.realfood.clients.infrastructure.api.security

import es.urjc.realfood.clients.domain.services.JWTService
import es.urjc.realfood.clients.domain.services.JWTService.Companion.CLIENT_ROLE
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class JWTGeneratorService(
    @Value("\${JWT_SECRET}") private val tokenSecret: String,
    @Value("\${JWT_ISSUER}") private val issuer: String
) : JWTService {

    override fun generateJwt(userId: String): String {
        return Jwts.builder()
            .setIssuedAt(Date.from(Instant.now()))
            .setSubject(userId)
            .setIssuer(issuer)
            .setExpiration(Date.from(Instant.now().plusSeconds(60)))
            .addClaims(mapOf("role" to CLIENT_ROLE))
            .signWith(
                SignatureAlgorithm.HS256,
                Base64.getEncoder().encodeToString(tokenSecret.encodeToByteArray()).toString()
            )
            .compact()
    }

}