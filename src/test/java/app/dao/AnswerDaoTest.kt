package app.dao

import app.model.Answer
import core.jdbc.ConnectionManager
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.core.io.ClassPathResource
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator

internal class AnswerDaoTest {

    private lateinit var answerDao: AnswerDao

    @BeforeEach
    fun setup() {
        val populator = ResourceDatabasePopulator()
        populator.addScript(ClassPathResource("jwp.sql"))
        DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource())
        answerDao = AnswerDao()
    }

    @Test
    fun insert() {

        val expected = Answer(1, "keeseung", "test")
        val actual = answerDao.insert(expected)

        assertEquals(expected.questionId, actual?.questionId)
        assertEquals(expected.contents, actual?.contents)
        assertEquals(expected.writer, actual?.writer)

    }
}