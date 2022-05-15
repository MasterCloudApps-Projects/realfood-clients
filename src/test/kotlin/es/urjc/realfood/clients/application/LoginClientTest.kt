package es.urjc.realfood.clients.application

abstract class LoginClientTest {

    protected fun validLoginClientRequest(): LoginClientRequest {
        return LoginClientRequest(
            email = "cristofer@cristofer.es",
            password = "1234"
        )
    }

    protected fun invalidEmailLoginClientRequest(): LoginClientRequest {
        return LoginClientRequest(
            email = "cristofer",
            password = "1234"
        )
    }

}