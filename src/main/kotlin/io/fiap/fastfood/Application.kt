package io.fiap.fastfood

import org.springframework.boot.SpringApplication
import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class Application

fun main(args: Array<String>) {
    val app = SpringApplication(Application::class.java)
    app.webApplicationType = WebApplicationType.REACTIVE
    app.run(*args)
}