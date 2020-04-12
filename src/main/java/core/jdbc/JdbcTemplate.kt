package core.jdbc

import java.sql.PreparedStatement
import java.sql.ResultSet

abstract class JdbcTemplate {

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


    open fun query(sql: String): List<Any> {
        val con = ConnectionManager.getConnection()
        return con.use {
            val prepareStatement = it.prepareStatement(sql)
            val resultSet = prepareStatement.use { ps ->
                setValues(ps)
                val executeQuery = ps.executeQuery()
                executeQuery
            }

            val result: MutableList<Any> = mutableListOf()
            while (resultSet.next()) {
                result.add(resultSet)
            }

            result
        }
    }

    fun queryObject(sql: String): Any? {
        val result = query(sql)
        if(result.isEmpty()) {
            return null
        }

        return result[0]
    }



    abstract fun setValues(pstmt: PreparedStatement)

    abstract fun mapRow(resultSet: ResultSet): Any?

}