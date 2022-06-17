package es.urjc.realfood.clients.infrastructure.external.services

import com.fasterxml.jackson.databind.ObjectMapper
import es.urjc.realfood.clients.domain.exception.ProductException
import es.urjc.realfood.clients.domain.services.CheckoutCartService
import es.urjc.realfood.clients.domain.services.CheckoutServiceRequest
import es.urjc.realfood.clients.domain.services.CheckoutServiceResponse
import es.urjc.realfood.clients.infrastructure.config.RetrofitConfig
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.springframework.stereotype.Component

@Component
class CheckoutService(
    private val retrofitConfig: RetrofitConfig
) : CheckoutCartService {

    private val objectMapper = ObjectMapper()
        .findAndRegisterModules()

    override fun invoke(request: CheckoutServiceRequest, token: String): CheckoutServiceResponse {
        val client: OkHttpClient = OkHttpClient().newBuilder()
            .build()
        val mediaType = "application/json".toMediaTypeOrNull()
        val body: RequestBody = objectMapper.writeValueAsString(request).toRequestBody(mediaType)
        val request: Request = Request.Builder()
            .url(retrofitConfig.checkoutEndpoint())
            .method("POST", body)
            .addHeader("Authorization", "Bearer $token")
            .addHeader("Content-Type", "application/json")
            .build()
        val response: Response = client.newCall(request).execute()

        if (response.code != 200)
            throw ProductException("Error from Restaurants API")

        return objectMapper.readValue(response.body!!.charStream(), CheckoutServiceResponse::class.java)
    }

}