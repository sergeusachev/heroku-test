package ru.myback01.feature.login

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.myback01.database.token.TokenDTO
import ru.myback01.database.token.TokenTable
import ru.myback01.database.user.UserTable
import java.util.*

class LoginController(private val call: ApplicationCall) {

    suspend fun performLogin() {
        val loginRequest = call.receive<LoginRequest>()

        val userDTO = UserTable.fetchUser(loginRequest.login)

        userDTO?.let {
            if (it.password == loginRequest.password) {
                val token = UUID.randomUUID().toString()

                TokenTable.insert(
                    TokenDTO(
                        id = UUID.randomUUID().toString(),
                        login = loginRequest.login,
                        token = token
                    )
                )

                call.respond(LoginResponse(token = token))
            } else {
                call.respond(HttpStatusCode.BadRequest, "Wrong password.")
            }
        } ?: call.respond(HttpStatusCode.NotFound, "User doesn't exist.")
    }
}