package es.urjc.realfood.clients.application

import es.urjc.realfood.clients.domain.ClientObjectProvider.Companion.validClientId

abstract class FindByClientIdClientCartTest {

    protected fun validFindByClientIdCartRequest(): FindByClientIdCartRequest {
        return FindByClientIdCartRequest(
            clientId = validClientId().toString()
        )
    }

    protected fun invalidFindByClientIdCartRequest(): FindByClientIdCartRequest {
        return FindByClientIdCartRequest(
            clientId = "ILLEGAL"
        )
    }

}