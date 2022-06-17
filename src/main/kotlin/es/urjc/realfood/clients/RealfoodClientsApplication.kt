package es.urjc.realfood.clients

import org.springframework.amqp.core.Queue
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class RealfoodClientsApplication {

    @Bean
    fun payments(): Queue {
        return Queue("payments")
    }

    @Bean
    fun preparedOrders(): Queue {
        return Queue("prepared-orders")
    }

    @Bean
    fun sentOrders(): Queue {
        return Queue("sent-orders")
    }

    @Bean
    fun completedOrders(): Queue {
        return Queue("completed-orders")
    }

}

fun main(args: Array<String>) {
    runApplication<RealfoodClientsApplication>(*args)
}
