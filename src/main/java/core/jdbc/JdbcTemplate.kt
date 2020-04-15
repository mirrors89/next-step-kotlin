package core.jdbc

class JdbcTemplate {

    fun update(sql: String, pss: PreparedStatementSetter) {
        val con = ConnectionManager.getConnection()
        con.use {
            val prepareStatement = it.prepareStatement(sql)
            prepareStatement.use {ps ->
                pss.setValues(ps)
                ps.executeUpdate()
            }
        }
    }


    open fun query(sql: String, pss: PreparedStatementSetter, rowMapper: RowMapper): List<Any?> {
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
    }

    fun queryObject(sql: String, pss: PreparedStatementSetter, rowMapper: RowMapper): Any? {
        val result = query(sql, pss, rowMapper)
        if(result.isEmpty()) {
            return null
        }

        return result[0]
    }
}