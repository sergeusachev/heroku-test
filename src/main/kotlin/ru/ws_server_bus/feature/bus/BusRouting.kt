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
        val wsConnections = Collections.synchronizedSet<WsConnection?>(LinkedHashSet())
        webSocket("/echoN") {
            val thisWsConnection = WsConnection(this)
            wsConnections += thisWsConnection
            send("You've logged in as [${thisWsConnection.name}]")

            for (frame in incoming) {
                frame as? Frame.Text ?: continue
                val receivedText = frame.readText()
                val textWithUsername = "[${thisWsConnection.name}]: $receivedText"
                wsConnections.forEach {
                    it.session.send(textWithUsername)
                }
            }
        }
    }
}