package core.jdbc

import app.model.User
import java.sql.PreparedStatement

abstract class JdbcTemplate {
    fun update(user: User) {
        val con = ConnectionManager.getConnection()
        con.use {
            val prepareStatement = it.prepareStatement(createQuery())
            prepareStatement.use {ps ->
                setValues(user, ps)
                ps.executeUpdate()
            }
        }
    }

    abstract fun setValues(user: User, pstmt: PreparedStatement)

    abstract fun createQuery(): String
}