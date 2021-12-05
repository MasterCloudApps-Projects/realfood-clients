package es.urjc.realfood.clients.domain.services

interface AuthService {

    operator fun invoke(registerRequest: RegisterRequest): RegisterResponse

}

data class RegisterRequest(
    val userId: String
)

data class RegisterResponse(
    val alreadyRegistered: Boolean
)
