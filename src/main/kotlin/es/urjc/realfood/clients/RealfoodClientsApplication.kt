package es.urjc.realfood.clients

import org.springframework.amqp.core.Queue
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class RealfoodClientsApplication {

    @Bean
    fun payments(): Queue {
        return Queue("payments", false)
    }

    @Bean
    fun preparedOrders(): Queue {
        return Queue("prepared-orders", false)
    }

}

fun main(args: Array<String>) {
    runApplication<RealfoodClientsApplication>(*args)
}
