package app.dao

import app.model.User
import core.jdbc.JdbcTemplate
import java.sql.PreparedStatement
import java.sql.ResultSet

class UserDao {
    fun insert(user: User) {
        val jdbcTemplate = object : JdbcTemplate() {
            override fun setValues(pstmt: PreparedStatement) {
                pstmt.setString(1, user.userId)
                pstmt.setString(2, user.password)
                pstmt.setString(3, user.name)
                pstmt.setString(4, user.email)
            }

            override fun mapRow(resultSet: ResultSet): Any? {
                return null
            }
        }

        val sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)"
        jdbcTemplate.update(sql)
    }

    fun findByUserId(userId: String): User? {
        val jdbcTemplate = object : JdbcTemplate() {
            override fun setValues(pstmt: PreparedStatement) {
                pstmt.setString(1, userId)
            }

            override fun mapRow(resultSet: ResultSet): Any? {
                return User(
                        resultSet.getString("userId"),
                        resultSet.getString("password"),
                        resultSet.getString("name"),
                        resultSet.getString("email"))
            }

        }

        val sql = "SELECT userId, password, name, email FROM USERS WHERE userId = ?"
        return jdbcTemplate.queryObject(sql) as User
    }

    fun update(user: User) {
        val jdbcTemplate = object : JdbcTemplate() {
            override fun setValues(pstmt: PreparedStatement) {
                pstmt.setString(1, user.password)
                pstmt.setString(2, user.name)
                pstmt.setString(3, user.email)
                pstmt.setString(4, user.userId)
            }

            override fun mapRow(resultSet: ResultSet): Any? {
                return null
            }
        }

        val sql = "UPDATE USERS SET password = ?, name = ?, email= ? WHERE userId = ?"
        jdbcTemplate.update(sql)

    }

    fun findAll(): List<User> {
        val jdbcTemplate = object : JdbcTemplate() {
            override fun setValues(pstmt: PreparedStatement) {
            }

            override fun mapRow(resultSet: ResultSet): Any? {
                return User(
                        resultSet.getString("userId"),
                        resultSet.getString("password"),
                        resultSet.getString("name"),
                        resultSet.getString("email"))
            }

        }

        val sql = "SELECT userId, password, name, email FROM USERS"
        return jdbcTemplate.query(sql)
                .filterIsInstance<User>()
    }

}