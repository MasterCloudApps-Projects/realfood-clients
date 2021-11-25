package es.urjc.realfood.clients.domain.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Client not found")
class ClientNotFoundException : RuntimeException() {
}