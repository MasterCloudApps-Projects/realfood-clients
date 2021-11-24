package es.urjc.realfood.clients.application

import es.urjc.realfood.clients.application.request.NewClientRequest
import es.urjc.realfood.clients.application.response.NewClientResponse
import org.springframework.stereotype.Service

@Service
class ClientService {

    fun save(newClientRequest: NewClientRequest): NewClientResponse {

        // mapear a objeto
        // llamar al cliente de registro - status pending

        // guardar en postgress

        // llamar al cliente de registro - status activado

        // devolver el dto

        TODO("Implementar")
    }

}