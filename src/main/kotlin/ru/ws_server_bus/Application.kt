package ru.ws_server_bus

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database
import ru.ws_server_bus.feature.login.configureLoginRouting
import ru.ws_server_bus.feature.register.configureRegisterRouting
import ru.ws_server_bus.feature.sendpoll.configureSendPollRouting
import ru.ws_server_bus.plugins.configureSerialization
import ru.ws_server_bus.plugins.configureWebSockets

fun main() {
    Database.connect(
        url = "jdbc:postgresql://localhost:5432/ws_hub_db",
        driver = "org.postgresql.Driver",
        user = "postgres",
        password = ""
    )

    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureSerialization()
        configureWebSockets()

        configureRegisterRouting()
        configureLoginRouting()
        configureSendPollRouting()
    }.start(wait = true)
}
