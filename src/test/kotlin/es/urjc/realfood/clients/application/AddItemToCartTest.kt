package es.urjc.realfood.clients.application

import es.urjc.realfood.clients.domain.CartObjectProvider.Companion.validItemId
import es.urjc.realfood.clients.domain.CartObjectProvider.Companion.validQuantity
import es.urjc.realfood.clients.domain.CartObjectProvider.Companion.validRestaurantId
import es.urjc.realfood.clients.domain.CartObjectProvider.Companion.zeroQuantity
import es.urjc.realfood.clients.domain.ClientObjectProvider.Companion.validClientId

abstract class AddItemToCartTest {

    protected fun validAddItemToCartRequest(): AddItemToCartRequest {
        return AddItemToCartRequest(
            clientId = validClientId().toString(),
            itemId = validItemId(),
            restaurantId = validRestaurantId(),
            quantity = validQuantity()
        )
    }

    protected fun invalidIdAddItemToCartRequest(): AddItemToCartRequest {
        return AddItemToCartRequest(
            clientId = "ILLEGAL",
            itemId = validItemId(),
            restaurantId = validRestaurantId(),
            quantity = validQuantity()
        )
    }

    protected fun invalidQuantityAddItemToCartRequest(): AddItemToCartRequest {
        return AddItemToCartRequest(
            clientId = validClientId().toString(),
            itemId = validItemId(),
            restaurantId = validRestaurantId(),
            quantity = zeroQuantity()
        )
    }

}