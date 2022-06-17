package es.urjc.realfood.clients.domain

import es.urjc.realfood.clients.domain.ClientObjectProvider.Companion.validClient

class CartObjectProvider {

    companion object {

        fun validCartId(): CartId = CartId("89a135b8-98dc-4e57-a22f-b5f99c6b1a99")

        fun validCartIdString(): String = "89a135b8-98dc-4e57-a22f-b5f99c6b1a99"

        fun validItemId(): String = "89a135b8-98dc-4e57-a22f-b5f99c6b1a00"

        fun validRestaurantId(): String = "89a135b8-98dc-4e57-a22f-b5f99c6b1a10"

        fun validQuantity(): Int = 5

        fun zeroQuantity(): Int = 0

        fun validCart(): Cart {
            val cart = Cart(
                id = validCartId(),
                client = validClient()
            )
            cart.items[validItemId()] = validCartItem()
            return cart
        }

        fun emptyCart(): Cart {
            return Cart(
                id = validCartId(),
                client = validClient()
            )
        }

        fun validCartItem(): CartItem {
            return CartItem(
                itemId = validItemId(),
                restaurantId = validRestaurantId(),
                quantity = validQuantity()
            )
        }

        fun validCartItemDto(): es.urjc.realfood.clients.domain.services.CartItemDto {
            return es.urjc.realfood.clients.domain.services.CartItemDto(
                itemId = validItemId(),
                qty = validQuantity()
            )
        }

    }

}