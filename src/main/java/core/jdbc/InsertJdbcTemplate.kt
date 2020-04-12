package core.jdbc

import app.model.User
import java.sql.PreparedStatement

class InsertJdbcTemplate {
    fun insert(user: User) {
        val con = ConnectionManager.getConnection()
        con.use {
            val prepareStatement = it.prepareStatement(createQueryForInsert())
            prepareStatement.use { ps ->
                setValuesForInsert(user, ps)
                ps.executeUpdate()
            }
        }
    }


    fun setValuesForInsert(user: User, pstmt: PreparedStatement) {
        pstmt.setString(1, user.userId)
        pstmt.setString(2, user.password)
        pstmt.setString(3, user.name)
        pstmt.setString(4, user.email)
    }

    fun createQueryForInsert(): String {
        return "INSERT INTO USERS VALUES (?, ?, ?, ?)"
    }
}