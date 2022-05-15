package es.urjc.realfood.clients.application

import es.urjc.realfood.clients.domain.CartObjectProvider.Companion.validItemId
import es.urjc.realfood.clients.domain.CartObjectProvider.Companion.validQuantity
import es.urjc.realfood.clients.domain.CartObjectProvider.Companion.zeroQuantity
import es.urjc.realfood.clients.domain.ClientObjectProvider.Companion.validClientId
import es.urjc.realfood.clients.domain.services.FindByIdProductServiceRequest
import es.urjc.realfood.clients.domain.services.FindByIdProductServiceResponse

abstract class AddItemToCartTest {

    protected fun validAddItemToCartRequest(): AddItemToCartRequest {
        return AddItemToCartRequest(
            clientId = validClientId().toString(),
            itemId = validItemId(),
            quantity = validQuantity()
        )
    }

    protected fun invalidIdAddItemToCartRequest(): AddItemToCartRequest {
        return AddItemToCartRequest(
            clientId = "ILLEGAL",
            itemId = validItemId(),
            quantity = validQuantity()
        )
    }

    protected fun invalidQuantityAddItemToCartRequest(): AddItemToCartRequest {
        return AddItemToCartRequest(
            clientId = validClientId().toString(),
            itemId = validItemId(),
            quantity = zeroQuantity()
        )
    }

    protected fun validFindByIdProductServiceRequest(): FindByIdProductServiceRequest {
        return FindByIdProductServiceRequest(
            id = validItemId()
        )
    }

    protected fun validFindByIdProductServiceResponse(): FindByIdProductServiceResponse {
        return FindByIdProductServiceResponse(
            status = 200
        )
    }

    protected fun invalidFindByIdProductServiceResponse404(): FindByIdProductServiceResponse {
        return FindByIdProductServiceResponse(
            status = 404
        )
    }

}