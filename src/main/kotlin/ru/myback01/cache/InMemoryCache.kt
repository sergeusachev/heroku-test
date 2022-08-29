package ru.myback01.cache

import ru.myback01.feature.register.RegisterRequest

data class TokenCache(
    val login: String,
    val token: String
)

object InMemoryCache {
    private val userList: MutableList<RegisterRequest> = mutableListOf()
    private val tokenList: MutableList<TokenCache> = mutableListOf()

    fun addUser(user: RegisterRequest) {
        userList.add(user)
    }

    fun isUserExist(login: String): Boolean {
        return userList.map { it.login }.contains(login)
    }

    fun getUserOrNull(login: String): RegisterRequest? {
        return userList.firstOrNull { it.login == login }
    }

    fun addTokenForUser(login: String, token: String) {
        tokenList.add(
            TokenCache(
                login = login,
                token = token
            )
        )
    }
}