package es.urjc.realfood.clients.domain.services

interface FindByIdProductService {

    operator fun invoke(request: FindByIdProductServiceRequest): FindByIdProductServiceResponse

}

data class FindByIdProductServiceRequest(
    val id: String
)

data class FindByIdProductServiceResponse(
    val status: Int
)