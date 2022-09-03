package ru.ws_server_bus.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {

    routing {
        get("/getHelloWorld") {
            println("getHelloWorld in thread: ${Thread.currentThread()}")
            call.respondText("Hello World!")
        }
    }
}
