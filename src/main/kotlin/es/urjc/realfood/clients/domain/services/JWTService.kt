package es.urjc.realfood.clients.domain.services

interface JWTService {

    fun generateJwt(userId: String): String

    companion object {
        const val CLIENT_ROLE = "user"
    }

}