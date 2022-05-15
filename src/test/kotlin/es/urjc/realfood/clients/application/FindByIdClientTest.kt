package es.urjc.realfood.clients.application

import es.urjc.realfood.clients.domain.ClientObjectProvider.Companion.validClientIdString

abstract class FindByIdClientTest {

    protected fun validFindByIdClientRequest(): FindByIdClientRequest {
        return FindByIdClientRequest(
            id = validClientIdString()
        )
    }

    protected fun invalidFindByIdClientRequest(): FindByIdClientRequest {
        return FindByIdClientRequest(
            id = "INVALID-ID"
        )
    }

}