package es.urjc.realfood.clients.application

import es.urjc.realfood.clients.domain.ClientObjectProvider.Companion.validClientIdString

abstract class DeleteClientTest {

    protected fun validDeleteClientRequest(): DeleteClientRequest {
        return DeleteClientRequest(
            id = validClientIdString()
        )
    }

}