package es.urjc.realfood.clients.application

abstract class RegisterClientTest {

    protected fun validRegisterClientRequest(): RegisterClientRequest {
        return RegisterClientRequest(
            name = "Cristofer",
            lastName = "Lopez",
            email = "cristofer@cristofer.es",
            password = "1234"
        )
    }

    protected fun invalidEmailRegisterClientRequest(): RegisterClientRequest {
        return RegisterClientRequest(
            name = "Cristofer",
            lastName = "Lopez",
            email = "",
            password = "1234"
        )
    }

    protected fun invalidNameRegisterClientRequest(): RegisterClientRequest {
        return RegisterClientRequest(
            name = "",
            lastName = "Lopez",
            email = "cristofer@cristofer.es",
            password = "1234"
        )
    }

    protected fun invalidLastNameRegisterClientRequest(): RegisterClientRequest {
        return RegisterClientRequest(
            name = "Cristofer",
            lastName = "",
            email = "cristofer@cristofer.es",
            password = "1234"
        )
    }

}