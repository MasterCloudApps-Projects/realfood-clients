package es.urjc.realfood.clients.infrastructure.external.services

import es.urjc.realfood.clients.domain.services.AuthService
import es.urjc.realfood.clients.domain.services.RegisterRequest
import es.urjc.realfood.clients.domain.services.RegisterResponse
import org.springframework.stereotype.Component

@Component
class FakeAuthService : AuthService {

    override fun invoke(registerRequest: RegisterRequest): RegisterResponse {
        return RegisterResponse(
            alreadyRegistered = false
        )
    }

}