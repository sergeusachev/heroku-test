package ru.myback01.database.token

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import ru.myback01.database.user.UserTable

object TokenTable : Table(name = "tokens") {

    private val id = TokenTable.varchar("id", 64)
    private val login = TokenTable.varchar("login", 25)
    private val token = TokenTable.varchar("token", 64)

    fun insert(tokenDTO: TokenDTO) {
        transaction {
            TokenTable.insert {
                it[id] = tokenDTO.id
                it[login] = tokenDTO.login
                it[token] = tokenDTO.token
            }
        }
    }

    fun fetchToken(login: String): TokenDTO {
        val tokenResult = TokenTable.select {
            TokenTable.login.eq(login)
        }.single()

        return TokenDTO(
            id = tokenResult[TokenTable.id],
            login = tokenResult[TokenTable.login],
            token = tokenResult[TokenTable.token]
        )
    }
}