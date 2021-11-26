package es.urjc.realfood.clients.infrastructure.external.services

import es.urjc.realfood.clients.domain.services.*

class HttpAuthService(
        private val loginServiceClient: RetrofitLoginServiceApi
) : AuthService {

    override fun invoke(registerRequest: RegisterRequest): RegisterResponse {
        TODO("Not yet implemented")
    }

}

interface RetrofitLoginServiceApi {

}