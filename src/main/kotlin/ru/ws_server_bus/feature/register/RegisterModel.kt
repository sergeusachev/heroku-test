package ru.ws_server_bus.feature.register

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val login: String,
    val email: String,
    val password: String
)

@Serializable
data class RegisterResponse(
    val token: String
)
