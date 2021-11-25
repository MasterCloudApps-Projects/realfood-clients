package es.urjc.realfood.clients.application.request

data class ClientCredentialsRequest(
        val email: String,
        val password: String
){
}