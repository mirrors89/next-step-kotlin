package core.jdbc

import java.sql.ResultSet
import java.sql.SQLException

class JdbcTemplate {

    @Throws(DataAccessException::class)
    fun update(sql: String, vararg parameter : Any?) {
        try {
            val con = ConnectionManager.getConnection()
            con.use {
                val prepareStatement = it.prepareStatement(sql)
                prepareStatement.use {ps ->
                    for(index in parameter.indices) {
                        ps.setObject(index + 1, parameter[index])
                    }
                    ps.executeUpdate()
                }
            }
        } catch (e : SQLException) {
            throw DataAccessException(e.message)
        }
    }

    @Throws(DataAccessException::class)
    fun <T> query(sql: String, vararg parameter: Any, rowMapper: (ResultSet) -> T): List<T> {
        try {

            val con = ConnectionManager.getConnection()
            con.use {
                val prepareStatement = it.prepareStatement(sql)
                prepareStatement.use { ps ->
                    for(index in parameter.indices) {
                        ps.setObject(index + 1, parameter[index])
                    }

                    val resultSet = ps.executeQuery()

                    val result: MutableList<T> = mutableListOf()
                    while (resultSet.next()) {
                        result.add(rowMapper(resultSet))
                    }

                    return result
                }

            }
        } catch (e : SQLException) {
            throw DataAccessException(e.message)
        }
    }

    @Throws(DataAccessException::class)
    fun <T> queryObject(sql: String, vararg parameter: Any, rowMapper: (ResultSet) -> T): T? {
        val result = query(sql, parameter, rowMapper = rowMapper)
        return result.firstOrNull()
    }
}