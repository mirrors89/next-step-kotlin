package core.jdbc

import app.dao.UserDao
import app.model.User

class InsertJdbcTemplate {
    fun insert(user: User, userDao: UserDao) {
        val con = ConnectionManager.getConnection()
        con.use {
            val prepareStatement = it.prepareStatement(userDao.createQueryForInsert())
            prepareStatement.use { ps ->
                userDao.setValuesForInsert(user, ps)
                ps.executeUpdate()
            }
        }
    }
}