package app.dao

import app.model.Question
import core.jdbc.ConnectionManager
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.core.io.ClassPathResource
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator

internal class QuestionDaoTest {

    private lateinit var questionDao : QuestionDao

    @BeforeEach
    fun setup() {
        val populator = ResourceDatabasePopulator()
        populator.addScript(ClassPathResource("jwp.sql"))
        DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource())
        questionDao = QuestionDao()
    }

    @Test
    fun crud() {
        val expected = Question(9, "keeseung", "타이틀입니다.", "내용입니다.")

        questionDao.insert(expected)

        var actual: Question? = questionDao.findById(expected.questionId)
        assertEquals(expected, actual)

        expected.update(Question(9, "keeseung", "수정된 타이틀 입니다.", "수정된 내용입니다.", 5))
        questionDao.update(expected)
        actual = questionDao.findById(expected.questionId)
        assertEquals(expected, actual)

    }

    @Test
    @Throws(Exception::class)
    fun findAll() {
        val questions: List<Question> = questionDao.findAll()
        assertEquals(8, questions.size)
    }
}