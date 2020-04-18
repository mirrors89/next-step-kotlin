package app.dao

import app.model.User
import core.jdbc.JdbcTemplate
import core.jdbc.RowMapper
import java.sql.ResultSet

class UserDao {
    fun insert(user: User) {
        val jdbcTemplate = JdbcTemplate()

        val sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)"
        jdbcTemplate.update(sql,
                user.userId,
                user.password,
                user.name,
                user.email)
    }

    fun findByUserId(userId: String): User? {
        val jdbcTemplate = JdbcTemplate()

        val sql = "SELECT userId, password, name, email " +
                "FROM USERS " +
                "WHERE userId = ?"

        return jdbcTemplate.queryObject(sql, userId) {
            User(
                    it.getString("userId"),
                    it.getString("password"),
                    it.getString("name"),
                    it.getString("email"))

        }
    }

    fun update(user: User) {
        val jdbcTemplate = JdbcTemplate()

        val sql = "UPDATE USERS SET password = ?, name = ?, email= ? WHERE userId = ?"
        jdbcTemplate.update(sql,
                user.password,
                user.name,
                user.email,
                user.userId)

    }

    fun findAll(): List<User> {
        val jdbcTemplate = JdbcTemplate()

        val sql = "SELECT userId, password, name, email " +
                "FROM USERS"
        return jdbcTemplate.query(sql) {
            User(
                    it.getString("userId"),
                    it.getString("password"),
                    it.getString("name"),
                    it.getString("email"))

        }
    }

}