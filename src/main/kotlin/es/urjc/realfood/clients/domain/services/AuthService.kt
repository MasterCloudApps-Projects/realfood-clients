package es.urjc.realfood.clients.domain.services

interface AuthService {

    operator fun invoke(registerRequest: RegisterRequest): RegisterResponse

    companion object {
        const val CLIENT_ROLE = "user"
    }

}

data class RegisterRequest(
        val email: String,
        val password: String,
        val role: String
)

data class RegisterResponse(
        val token: String,
        val userId: String
)
