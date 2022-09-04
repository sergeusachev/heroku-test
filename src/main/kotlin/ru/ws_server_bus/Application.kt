package ru.ws_server_bus

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database
import ru.ws_server_bus.feature.bus.configureBusRouting
import ru.ws_server_bus.feature.login.configureLoginRouting
import ru.ws_server_bus.feature.register.configureRegisterRouting
import ru.ws_server_bus.plugins.configureRouting
import ru.ws_server_bus.plugins.configureSerialization
import ru.ws_server_bus.plugins.configureWebSockets

fun main() {
    Database.connect(
        url = "jdbc:postgresql://localhost:5432/test_db",
        driver = "org.postgresql.Driver",
        user = "postgres",
        password = "111"
    )

    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureSerialization()
        configureWebSockets()

        configureRouting()
        configureRegisterRouting()
        configureLoginRouting()
        configureBusRouting()
    }.start(wait = true)
}
