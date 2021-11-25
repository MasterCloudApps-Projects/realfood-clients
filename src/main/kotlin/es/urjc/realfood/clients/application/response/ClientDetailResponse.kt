package es.urjc.realfood.clients.application.response

data class ClientDetailResponse(
        val id: String,
        val name: String,
        val lastName: String,
        val userId: String
) {
}