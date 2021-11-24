package es.urjc.realfood.clients.application.request

data class NewClientRequest(
        val name: String,
        val lastName: String,
        val email: String,
        val password: String
) {
}