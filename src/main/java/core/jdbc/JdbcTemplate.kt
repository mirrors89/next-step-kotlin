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
    fun <T> query(sql: String, pss: PreparedStatementSetter, rowMapper: RowMapper<T>): List<T> {
        try {

            val con = ConnectionManager.getConnection()
            con.use {
                val prepareStatement = it.prepareStatement(sql)
                prepareStatement.use { ps ->
                    pss.setValues(ps)
                    val resultSet = ps.executeQuery()

                    val result: MutableList<T> = mutableListOf()
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
    fun <T> queryObject(sql: String, pss: PreparedStatementSetter, rowMapper: RowMapper<T>): T? {
        val result = query(sql, pss, rowMapper)
        if(result.isEmpty()) {
            return null
        }

        return result[0]
    }
}