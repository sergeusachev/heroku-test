package ru.myback01.feature.register

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.jetbrains.exposed.sql.insert
import ru.myback01.database.token.TokenDTO
import ru.myback01.database.token.TokenTable
import ru.myback01.database.user.UserDTO
import ru.myback01.database.user.UserTable
import ru.myback01.utils.isValidEmail
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