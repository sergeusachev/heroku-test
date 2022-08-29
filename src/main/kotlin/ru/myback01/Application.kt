package ru.myback01

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Database
import ru.myback01.feature.login.configureLoginRouting
import ru.myback01.feature.register.RegisterController
import ru.myback01.feature.register.configureRegisterRouting
import ru.myback01.plugins.*

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
