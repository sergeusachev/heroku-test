package ru.ws_server_bus.feature.bus

import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*

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
}