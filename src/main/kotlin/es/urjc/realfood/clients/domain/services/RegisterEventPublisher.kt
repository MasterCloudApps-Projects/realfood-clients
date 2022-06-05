package es.urjc.realfood.clients.domain.services

interface RegisterEventPublisher {

    operator fun invoke(registerEvent: RegisterEvent)

}

data class RegisterEvent(
    val clientId: String
)