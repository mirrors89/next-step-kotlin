package app.dao

import app.model.User
import core.jdbc.ConnectionManager
import core.jdbc.InsertJdbcTemplate
import core.jdbc.UpdateJdbcTemplate
import java.sql.PreparedStatement

class UserDao {
    fun insert(user: User) {
        val insertJdbcTemplate = InsertJdbcTemplate()
        insertJdbcTemplate.insert(user, this)
    }

    fun findByUserId(userId: String): User? {
        val con = ConnectionManager.getConnection()
        return con.use {
            val sql = "SELECT userId, password, name, email FROM USERS WHERE userId = ?"
            val prepareStatement = it.prepareStatement(sql)
            val user = prepareStatement.use { ps ->
                ps.setString(1, userId)
                val result = ps.executeQuery()
                var user: User? = null
                if (result.next()) {
                    user = User(
                            result.getString("userId"),
                            result.getString("password"),
                            result.getString("name"),
                            result.getString("email"))
                }

                user
            }
            user
        }
    }

    fun update(user: User) {
        val updateJdbcTemplate = UpdateJdbcTemplate()
        updateJdbcTemplate.update(user, this)

    }

    fun findAll(): List<User> {
        val users: MutableList<User> = mutableListOf()
        val con = ConnectionManager.getConnection()
        con.use {
            val sql = "SELECT userId, password, name, email FROM USERS"
            val prepareStatement = it.prepareStatement(sql)
            prepareStatement.use { ps ->
                val result = ps.executeQuery()
                while (result.next()) {
                    users.add(User(
                            result.getString("userId"),
                            result.getString("password"),
                            result.getString("name"),
                            result.getString("email")))
                }

            }
        }

        return users
    }

    fun setValuesForInsert(user: User, pstmt: PreparedStatement) {
        pstmt.setString(1, user.userId)
        pstmt.setString(2, user.password)
        pstmt.setString(3, user.name)
        pstmt.setString(4, user.email)
    }

    fun createQueryForInsert(): String {
        return "INSERT INTO USERS VALUES (?, ?, ?, ?)"
    }

    fun setValuesForUpdate(user: User, pstmt: PreparedStatement) {
        pstmt.setString(1, user.password)
        pstmt.setString(2, user.name)
        pstmt.setString(3, user.email)
        pstmt.setString(4, user.userId)
    }

    fun createQueryForUpdate(): String {
        return "UPDATE USERS SET password = ?, name = ?, email= ? WHERE userId = ?"
    }
}