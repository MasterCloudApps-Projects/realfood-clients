package es.urjc.realfood.clients.infrastructure.api.rest

import com.fasterxml.jackson.databind.ObjectMapper
import es.urjc.realfood.clients.application.RegisterClientRequest
import es.urjc.realfood.clients.application.RegisterClientResponse
import es.urjc.realfood.clients.domain.ClientObjectProvider.Companion.validClientIdString
import es.urjc.realfood.clients.domain.ClientObjectProvider.Companion.validJwt

abstract class RegisterRestControllerTest {

    private val objectMapper = ObjectMapper()

    protected fun validRegisterClientRequestJson(): String {
        return objectMapper.writeValueAsString(validRegisterClientRequest())
    }

    protected fun validRegisterClientRequest(): RegisterClientRequest {
        return RegisterClientRequest(
            name = "Cristofer",
            lastName = "Lopez",
            email = "cristofer@cristofer.es",
            password = "1234"
        )
    }

    protected fun validRegisterClientResponse(): RegisterClientResponse {
        return RegisterClientResponse(
            clientId = validClientIdString(),
            token = validJwt()
        )
    }

}