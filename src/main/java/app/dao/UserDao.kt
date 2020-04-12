package app.dao

import app.model.User
import core.jdbc.JdbcTemplate
import core.jdbc.SelectJdbcTemplate
import java.sql.PreparedStatement
import java.sql.ResultSet

class UserDao {
    fun insert(user: User) {
        val insertJdbcTemplate = object : JdbcTemplate() {
            override fun setValues(pstmt: PreparedStatement) {
                pstmt.setString(1, user.userId)
                pstmt.setString(2, user.password)
                pstmt.setString(3, user.name)
                pstmt.setString(4, user.email)
            }

            override fun createQuery(): String {
                return "INSERT INTO USERS VALUES (?, ?, ?, ?)"
            }
        }
        insertJdbcTemplate.update()
    }

    fun findByUserId(userId: String): User? {
        val selectJdbcTemplate = object : SelectJdbcTemplate() {
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
        return selectJdbcTemplate.queryObject(sql) as User
    }

    fun update(user: User) {
        val jdbcTemplate = object : JdbcTemplate() {
            override fun setValues(pstmt: PreparedStatement) {
                pstmt.setString(1, user.password)
                pstmt.setString(2, user.name)
                pstmt.setString(3, user.email)
                pstmt.setString(4, user.userId)
            }

            override fun createQuery(): String {
                return "UPDATE USERS SET password = ?, name = ?, email= ? WHERE userId = ?"
            }
        }
        jdbcTemplate.update()

    }

    fun findAll(): List<User> {
        val selectJdbcTemplate = object : SelectJdbcTemplate() {
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
        return selectJdbcTemplate.query(sql)
                .filterIsInstance<User>()
    }

}