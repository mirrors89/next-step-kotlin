package core.jdbc

import java.sql.PreparedStatement

abstract class JdbcTemplate {
    fun update() {
        val con = ConnectionManager.getConnection()
        con.use {
            val prepareStatement = it.prepareStatement(createQuery())
            prepareStatement.use {ps ->
                setValues(ps)
                ps.executeUpdate()
            }
        }
    }


    fun update(sql: String) {
        val con = ConnectionManager.getConnection()
        con.use {
            val prepareStatement = it.prepareStatement(sql)
            prepareStatement.use {ps ->
                setValues(ps)
                ps.executeUpdate()
            }
        }
    }

    abstract fun setValues(pstmt: PreparedStatement)

    abstract fun createQuery(): String
}