package core.jdbc

import java.sql.ResultSet
import java.sql.SQLException


class JdbcTemplate {

    @Throws(DataAccessException::class)
    fun update(sql: String, pss: PreparedStatementSetter) {
        try {
            val con = ConnectionManager.getConnection()
            con.use {
                val prepareStatement = it.prepareStatement(sql)
                prepareStatement.use {ps ->
                    pss.setValues(ps)
                    ps.executeUpdate()
                }
            }
        } catch (e : SQLException) {
            throw DataAccessException(e.message)
        }
    }

    fun update(sql: String, vararg parameter : Any?) {
        update(sql, createPreparedStatementSetter(*parameter))
    }

    fun update(psc: PreparedStatementCreator, holder: KeyHolder) {
        try {
            val con = ConnectionManager.getConnection()
            con.use {
                val ps = psc.createPreparedStatement(con)
                ps.executeUpdate()

                val resultSet = ps.generatedKeys
                if (resultSet.next()) {
                    holder.id = resultSet.getLong(1)
                }
                resultSet.close()
            }
        } catch (e : SQLException) {
            throw DataAccessException(e.message)
        }
    }


    @Throws(DataAccessException::class)
    fun <T> query(sql: String, pss: PreparedStatementSetter, rowMapper: (ResultSet) -> T): List<T> {
        try {

            val con = ConnectionManager.getConnection()
            con.use {
                val prepareStatement = it.prepareStatement(sql)
                prepareStatement.use { ps ->
                    pss.setValues(ps)

                    val resultSet = ps.executeQuery()
                    val result: MutableList<T> = mutableListOf()
                    while (resultSet.next()) {
                        result.add(rowMapper(resultSet))
                    }
                    resultSet.close()

                    return result
                }

            }
        } catch (e : SQLException) {
            throw DataAccessException(e.message)
        }
    }

    fun <T> query(sql: String, vararg parameter: Any, rowMapper: (ResultSet) -> T): List<T> {
        return query(sql, createPreparedStatementSetter(*parameter), rowMapper)
    }

    fun <T> queryObject(sql: String, pss: PreparedStatementSetter, rowMapper: (ResultSet) -> T): T? {
        val result = query(sql, pss, rowMapper)
        return result.firstOrNull()
    }

    fun <T> queryObject(sql: String, vararg parameter: Any, rowMapper: (ResultSet) -> T): T? {
        return queryObject(sql, createPreparedStatementSetter(*parameter), rowMapper)
    }

    private fun createPreparedStatementSetter(vararg parameter : Any?): PreparedStatementSetter {
        return PreparedStatementSetter {
            for(index in parameter.indices) {
                it.setObject(index + 1, parameter[index])
            }
        }

    }
}