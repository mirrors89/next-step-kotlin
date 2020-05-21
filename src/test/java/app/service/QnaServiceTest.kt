package app.service

import app.CannotDeleteException
import app.dao.AnswerDao
import app.dao.QuestionDao
import app.model.Question
import app.model.User
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class QnaServiceTest {

    private lateinit var qnaService: QnaService

    @RelaxedMockK
    private lateinit var questionDao: QuestionDao
    @RelaxedMockK
    private lateinit var answerDao: AnswerDao


    @BeforeEach
    fun setup() {
        qnaService = QnaService(questionDao, answerDao)
    }

    @Test
    fun deleteQuestion_없는_질문() {
        assertThrows(CannotDeleteException::class.java) {

            every { questionDao.findById(1L) }
                    .returns(null)

            qnaService.deleteQuestion(1L, newUser("userId"))
        }

    }

    @Test
    fun deleteQuestion_다른_사용자() {
        assertThrows(CannotDeleteException::class.java) {
            val question = newQuestion(1L, "keeseung")
            every { questionDao.findById(1L) }.returns(question)
            every { answerDao.findAllByQuestionId(1L) }.returns(arrayListOf())
            qnaService.deleteQuestion(1L, newUser("userId"))
        }

    }

    @Test
    fun deleteQuestion_같은_사용자_답변없음() {
        val question = newQuestion(1L, "keeseung")
        every { questionDao.findById(1L) }.returns(question)
        every { answerDao.findAllByQuestionId(1L) }.returns(arrayListOf())
        qnaService.deleteQuestion(1L, newUser("keeseung"))
    }

    private fun newUser(userId: String): User {
        return User(userId, null, userId, null)
    }

    private fun newQuestion(questionId: Long, userId: String): Question {
        return Question(questionId, title = "Test", contents = "content Test", writer = userId)
    }

}