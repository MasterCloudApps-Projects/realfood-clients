package es.urjc.realfood.clients.application

import es.urjc.realfood.clients.domain.Cart
import es.urjc.realfood.clients.domain.CartObjectProvider.Companion.validCartId
import es.urjc.realfood.clients.domain.CartObjectProvider.Companion.validItemId
import es.urjc.realfood.clients.domain.ClientObjectProvider.Companion.validClient
import es.urjc.realfood.clients.domain.ClientObjectProvider.Companion.validClientId

abstract class DeleteItemFromCartTest {

    protected fun validDeleteItemFromCartRequest(): DeleteItemFromCartRequest {
        return DeleteItemFromCartRequest(
            clientId = validClientId().toString(),
            itemId = validItemId()
        )
    }

    protected fun invalidDeleteItemFromCartRequest(): DeleteItemFromCartRequest {
        return DeleteItemFromCartRequest(
            clientId = "ILLEGAL",
            itemId = validItemId()
        )
    }

    protected fun validCart(): Cart {
        return Cart(
            id = validCartId(),
            client = validClient()
        )
    }

}