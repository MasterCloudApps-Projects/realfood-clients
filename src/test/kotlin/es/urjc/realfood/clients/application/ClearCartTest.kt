package es.urjc.realfood.clients.application

import es.urjc.realfood.clients.domain.ClientObjectProvider.Companion.validClientId

abstract class ClearCartTest {

    protected fun validClearCartRequest(): ClearCartRequest {
        return ClearCartRequest(
            clientId = validClientId().toString()
        )
    }

    protected fun invalidClearCartRequest(): ClearCartRequest {
        return ClearCartRequest(
            clientId = "ILLEGAL"
        )
    }

}