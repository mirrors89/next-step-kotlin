package core.jdbc

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

    @Throws(DataAccessException::class)
    fun query(sql: String, pss: PreparedStatementSetter, rowMapper: RowMapper): List<Any?> {
        try {

            val con = ConnectionManager.getConnection()
            con.use {
                val prepareStatement = it.prepareStatement(sql)
                prepareStatement.use { ps ->
                    pss.setValues(ps)
                    val resultSet = ps.executeQuery()

                    val result: MutableList<Any?> = mutableListOf()
                    while (resultSet.next()) {
                        result.add(rowMapper.mapRow(resultSet))
                    }

                    return result
                }

            }
        } catch (e : SQLException) {
            throw DataAccessException(e.message)
        }
    }

    @Throws(DataAccessException::class)
    fun queryObject(sql: String, pss: PreparedStatementSetter, rowMapper: RowMapper): Any? {
        val result = query(sql, pss, rowMapper)
        if(result.isEmpty()) {
            return null
        }

        return result[0]
    }
}