package es.urjc.realfood.clients.infrastructure.external.client

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class LoginClient {

    @Value("\${login.client.host}")
    private lateinit var host: String

    @Value("\${login.client.port}")
    private lateinit var port: String

    private fun getUrl() : String = "https://${host}:${port}"

    fun login() {
        TODO("Not implemented")
    }

    fun register() {
        TODO("Not implemented")
    }

}