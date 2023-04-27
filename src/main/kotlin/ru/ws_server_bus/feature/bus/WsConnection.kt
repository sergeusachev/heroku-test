package ru.ws_server_bus.feature.bus

import io.ktor.server.websocket.*
import java.util.concurrent.atomic.AtomicInteger

class WsConnection(val session: DefaultWebSocketServerSession) {

    val name = "user${lastId.getAndIncrement()}"

    companion object {
        val lastId = AtomicInteger(0)
    }
}