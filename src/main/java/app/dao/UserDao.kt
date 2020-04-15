package app.dao

import app.model.User
import core.jdbc.JdbcTemplate
import core.jdbc.PreparedStatementSetter
import core.jdbc.RowMapper
import java.sql.ResultSet

class UserDao {
    fun insert(user: User) {
        val jdbcTemplate = JdbcTemplate()

        val pss = PreparedStatementSetter {
            it.setString(1, user.userId)
            it.setString(2, user.password)
            it.setString(3, user.name)
            it.setString(4, user.email)
        }

        val sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)"
        jdbcTemplate.update(sql, pss)
    }

    fun findByUserId(userId: String): User? {
        val jdbcTemplate = JdbcTemplate()

        val pss = PreparedStatementSetter {
            it.setString(1, userId)

        }

        val rowMapper = RowMapper {
            User(
                it.getString("userId"),
                it.getString("password"),
                it.getString("name"),
                it.getString("email"))

        }

        val sql = "SELECT userId, password, name, email FROM USERS WHERE userId = ?"
        return jdbcTemplate.queryObject(sql, pss, rowMapper) as User
    }

    fun update(user: User) {
        val jdbcTemplate = JdbcTemplate()

        val pss = PreparedStatementSetter {
            it.setString(1, user.password)
            it.setString(2, user.name)
            it.setString(3, user.email)
            it.setString(4, user.userId)
        }

        val sql = "UPDATE USERS SET password = ?, name = ?, email= ? WHERE userId = ?"
        jdbcTemplate.update(sql, pss)

    }

    fun findAll(): List<User> {
        val jdbcTemplate = JdbcTemplate()

        val pss = PreparedStatementSetter {
        }

        val rowMapper = RowMapper {
            User(
                it.getString("userId"),
                it.getString("password"),
                it.getString("name"),
                it.getString("email"))

        }

        val sql = "SELECT userId, password, name, email FROM USERS"
        return jdbcTemplate.query(sql, pss, rowMapper)
                .filterIsInstance<User>()
    }

}