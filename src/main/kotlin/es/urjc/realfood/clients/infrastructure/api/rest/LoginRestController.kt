package es.urjc.realfood.clients.infrastructure.api.rest

import es.urjc.realfood.clients.application.request.ClientCredentialsRequest
import es.urjc.realfood.clients.application.request.NewClientRequest
import es.urjc.realfood.clients.application.response.ClientCredentialsResponse
import es.urjc.realfood.clients.application.service.ClientService
import es.urjc.realfood.clients.infrastructure.config.FeatureFlagsSetup
import org.ff4j.FF4j
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class LoginRestController(
        private val clientService: ClientService,
        private val ff4j: FF4j,
        private val featureFlagsSetup: FeatureFlagsSetup
) : LoginRestApi {

    override fun register(newClientRequest: NewClientRequest): ResponseEntity<ClientCredentialsResponse> {
        return if (loginFeatureIsEnabled()) {
            val clientCredentialsResponse = clientService.register(newClientRequest)
            ResponseEntity.ok(clientCredentialsResponse)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    override fun login(clientCredentialsRequest: ClientCredentialsRequest): ResponseEntity<ClientCredentialsResponse> {
        return if (loginFeatureIsEnabled()) {
            val clientCredentialsResponse = clientService.login(clientCredentialsRequest)
            ResponseEntity.ok(clientCredentialsResponse)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    private fun loginFeatureIsEnabled(): Boolean {
        return ff4j.check(featureFlagsSetup.loginFeature)
    }

}