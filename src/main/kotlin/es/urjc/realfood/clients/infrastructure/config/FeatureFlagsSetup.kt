package es.urjc.realfood.clients.infrastructure.config

import org.ff4j.FF4j
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct


@Component
class FeatureFlagsSetup(
        val ff4j: FF4j
) {

    val loginFeature = "clients.feature.login"

    @PostConstruct
    fun setUpFlags() {
        ff4j.createFeature(loginFeature)

        // ff4j.enable(loginFeature)
    }

}