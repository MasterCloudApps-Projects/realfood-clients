package es.urjc.realfood.clients.infrastructure.external.services

import es.urjc.realfood.clients.domain.services.FindByIdProductService
import es.urjc.realfood.clients.domain.services.FindByIdProductServiceRequest
import es.urjc.realfood.clients.domain.services.FindByIdProductServiceResponse
import org.springframework.stereotype.Component

@Component
class FakeFindByIdProductService : FindByIdProductService {

    override fun invoke(request: FindByIdProductServiceRequest): FindByIdProductServiceResponse {
        return FindByIdProductServiceResponse(
            status = 200
        )
    }

}