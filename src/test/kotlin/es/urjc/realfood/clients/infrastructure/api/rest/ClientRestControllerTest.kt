package es.urjc.realfood.clients.infrastructure.api.rest

import com.fasterxml.jackson.databind.ObjectMapper
import es.urjc.realfood.clients.application.*
import es.urjc.realfood.clients.domain.ClientObjectProvider.Companion.validClientIdString
import es.urjc.realfood.clients.domain.ClientObjectProvider.Companion.validJwt

abstract class ClientRestControllerTest {

    private val objectMapper = ObjectMapper()

    protected fun validFindByIdClientRequestJson(): String {
        return objectMapper.writeValueAsString(validFindByIdClientRequest())
    }

    protected fun validFindByIdClientRequest(): FindByIdClientRequest {
        return FindByIdClientRequest(
            id = validClientIdString()
        )
    }

    protected fun validFindByIdClientResponse(): FindByIdClientResponse {
        return FindByIdClientResponse(
            id = validClientIdString(),
            name = "Cristofer",
            lastName = "Lopez",
            email = "cristofer@cristofer.es"
        )
    }

    protected fun validDeleteClientRequestJson(): String {
        return objectMapper.writeValueAsString(validDeleteClientRequest())
    }

    protected fun validDeleteClientRequest(): DeleteClientRequest {
        return DeleteClientRequest(
            id = validClientIdString()
        )
    }

    protected fun validLoginClientRequestJson(): String {
        return objectMapper.writeValueAsString(validLoginClientRequest())
    }

    protected fun validLoginClientRequest(): LoginClientRequest {
        return LoginClientRequest(
            email = "cristofer@cristofer.es",
            password = "1234"
        )
    }

    protected fun validLoginClientResponse(): LoginClientResponse {
        return LoginClientResponse(
            clientId = validClientIdString(),
            token = validJwt()
        )
    }

}