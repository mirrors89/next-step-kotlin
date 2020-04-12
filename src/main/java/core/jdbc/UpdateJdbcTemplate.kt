package core.jdbc

import app.dao.UserDao
import app.model.User

class UpdateJdbcTemplate {
    fun update(user: User, userDao: UserDao) {
        val con = ConnectionManager.getConnection()
        con.use {
            val prepareStatement = it.prepareStatement(userDao.createQueryForUpdate())
            prepareStatement.use {ps ->
                userDao.setValuesForUpdate(user, ps)
                ps.executeUpdate()
            }
        }
    }
}