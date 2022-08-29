package ru.myback01.database.user

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.Exception

object UserTable : Table(name = "users") {

    private val login = UserTable.varchar("login", 25)
    private val password = UserTable.varchar("password", 25)
    private val username = UserTable.varchar("username", 30)
    private val email = UserTable.varchar("email", 64) //TODO Как сделать nullable поле?

    fun insert(userDTO: UserDTO) {
        transaction {
            UserTable.insert {
                it[login] = userDTO.login
                it[password] = userDTO.password
                it[username] = userDTO.username
                it[email] = userDTO.email.orEmpty()
            }
        }
    }

    fun fetchUser(login: String): UserDTO? {
        return try {
            transaction {
                val userResult = UserTable.select {
                    UserTable.login.eq(login)
                }.single()

                UserDTO(
                    login = userResult[UserTable.login],
                    password = userResult[UserTable.password],
                    email = userResult[UserTable.email],
                    username = userResult[UserTable.username]
                )
            }
        } catch (e: Exception) {
            null
        }
    }
}