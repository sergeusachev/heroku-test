package ru.ws_server_bus.feature.bus

import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import java.util.Collections
import java.util.LinkedHashSet
import java.util.concurrent.atomic.AtomicInteger

fun Application.configureBusRouting() {

    routing {
        webSocket("/echo") {
            send("Please enter your name")
            for (frame in incoming) {
                frame as? Frame.Text ?: continue
                val receivedText = frame.readText()
                if (receivedText.equals(other = "bye", ignoreCase = true)) {
                    close(
                        CloseReason(
                            code = CloseReason.Codes.NORMAL,
                            message = "Client said BYE"
                        )
                    )
                } else {
                    send(Frame.Text(text = "Hi, $receivedText!"))
                }
            }
        }
    }

    routing {
        val connections = Collections.synchronizedSet<Connection?>(LinkedHashSet())
        webSocket("/echoN") {
            val thisConnection = Connection(this)
            connections += thisConnection
            send("You've logged in as [${thisConnection.name}]")

            for (frame in incoming) {
                frame as? Frame.Text ?: continue
                val receivedText = frame.readText()
                val textWithUsername = "[${thisConnection.name}]: $receivedText"
                connections.forEach {
                    it.session.send(textWithUsername)
                }
            }
        }
    }
}

class Connection(val session: DefaultWebSocketServerSession) {
    companion object {
        val lastId = AtomicInteger(0)
    }

    val name = "user${lastId.getAndIncrement()}"
}