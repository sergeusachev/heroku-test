package ru.ws_server_bus

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.jetbrains.exposed.sql.Database
import ru.ws_server_bus.feature.login.configureLoginRouting
import ru.ws_server_bus.feature.register.configureRegisterRouting
import ru.ws_server_bus.plugins.configureRouting
import ru.ws_server_bus.plugins.configureSerialization

fun main() {
    Database.connect(
        url = "jdbc:postgresql://localhost:5432/test_db",
        driver = "org.postgresql.Driver",
        user = "postgres",
        password = "111"
    )

    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()
        configureRegisterRouting()
        configureLoginRouting()
        configureSerialization()
    }.start(wait = true)
}
