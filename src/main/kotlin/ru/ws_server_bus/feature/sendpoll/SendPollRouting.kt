package ru.ws_server_bus.feature.sendpoll

import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import ru.ws_server_bus.feature.bus.WsConnection
import java.util.*

fun Application.configureSendPollRouting() {
    routing {
        val wsConnections = Collections.synchronizedSet<WsConnection?>(LinkedHashSet())
        webSocket("/bus") {
            val thisWsConnection = WsConnection(this)
            wsConnections += thisWsConnection

            try {
                send("You've logged in as [${thisWsConnection.name}]")
                for (frame in incoming) {
                    frame as? Frame.Text ?: continue
                    val receivedText = frame.readText()
                    if (receivedText.equals(other = "close", ignoreCase = true)) {
                        close(
                            CloseReason(
                                code = CloseReason.Codes.NORMAL,
                                message = "Client said BYE"
                            )
                        )
                    } else {
                        wsConnections.forEach {
                            if (it == thisWsConnection) return@forEach
                            it.session.send(frame)
                        }
                    }
                }
            } catch (e: Exception) {
                println(e.localizedMessage)
            } finally {
                println("Removing $thisWsConnection!")
                wsConnections -= thisWsConnection
            }
        }
    }
}

