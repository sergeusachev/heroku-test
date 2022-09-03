package ru.ws_server_bus.feature.register

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.ws_server_bus.database.token.TokenDTO
import ru.ws_server_bus.database.token.TokenTable
import ru.ws_server_bus.database.user.UserDTO
import ru.ws_server_bus.database.user.UserTable
import ru.ws_server_bus.utils.isValidEmail
import java.util.*

class RegisterController(private val call: ApplicationCall) {

    suspend fun registerNewUser() {
        val registerRequest = call.receive<RegisterRequest>()
        if (!registerRequest.email.isValidEmail()) {
            call.respond(HttpStatusCode.BadRequest, "Email is not valid.")
        }

        val userDTO = UserTable.fetchUser(registerRequest.login)

        if (userDTO != null) {
            call.respond(HttpStatusCode.Conflict, "User already exists.")
        }

        val token = UUID.randomUUID().toString()
        //val userId = UUID.randomUUID().toString()

        UserTable.insert(
            UserDTO(
                login = registerRequest.login,
                password = registerRequest.password,
                email = registerRequest.email,
                username = ""
            )
        )

        TokenTable.insert(
            TokenDTO(
                id = UUID.randomUUID().toString(),
                login = registerRequest.login,
                token = token
            )
        )

        call.respond(RegisterResponse(token = token))
    }
}