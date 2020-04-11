package app.dao

import app.model.User
import core.jdbc.ConnectionManager

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.core.io.ClassPathResource
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator


class UserDaoTest {
    private lateinit var userDao:UserDao
    @BeforeEach
    fun setup() {
        val populator = ResourceDatabasePopulator()
        populator.addScript(ClassPathResource("jwp.sql"))
        DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource())
        userDao = UserDao()
    }

    @Test
    @Throws(Exception::class)
    fun crud() {
        val expected = User("userId", "password", "name", "javajigi@email.com")

        userDao.insert(expected)

        var actual: User? = userDao.findByUserId(expected.userId)
        assertEquals(expected, actual)

        expected.update(User("userId", "password2", "name2", "sanjigi@email.com"))
        userDao.update(expected)
        actual = userDao.findByUserId(expected.userId)
        assertEquals(expected, actual)
    }

    @Test
    @Throws(Exception::class)
    fun findAll() {
        val userDao = UserDao()
        val users: List<User> = userDao.findAll()
        assertEquals(1, users.size)
    }
}