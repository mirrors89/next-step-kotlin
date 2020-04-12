package core.jdbc

import app.model.User
import java.sql.PreparedStatement

class UpdateJdbcTemplate {
    fun update(user: User) {
        val con = ConnectionManager.getConnection()
        con.use {
            val prepareStatement = it.prepareStatement(createQueryForUpdate())
            prepareStatement.use {ps ->
                setValuesForUpdate(user, ps)
                ps.executeUpdate()
            }
        }
    }



    fun setValuesForUpdate(user: User, pstmt: PreparedStatement) {
        pstmt.setString(1, user.password)
        pstmt.setString(2, user.name)
        pstmt.setString(3, user.email)
        pstmt.setString(4, user.userId)
    }

    fun createQueryForUpdate(): String {
        return "UPDATE USERS SET password = ?, name = ?, email= ? WHERE userId = ?"
    }
}