package es.urjc.realfood.clients.infrastructure.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class RetrofitConfig {

    @Value("\${restaurant.host}")
    private lateinit var host: String

    @Value("\${restaurant.port}")
    private lateinit var port: String

    fun checkoutEndpoint(): String = "https://${host}:${port}/api/orders"

}