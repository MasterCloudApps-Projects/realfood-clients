package es.urjc.realfood.clients.infrastructure.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class RetrofitConfig {

    @Value("\${login.client.host}")
    private lateinit var host: String

    @Value("\${login.client.port}")
    private lateinit var port: String

    private fun getUrl(): String = "https://${host}:${port}/register"

}