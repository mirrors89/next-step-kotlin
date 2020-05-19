package app.service

import app.CannotDeleteException
import app.dao.AnswerDao
import app.dao.MockAnswerDao
import app.dao.MockQuestionDao
import app.dao.QuestionDao
import app.model.Question
import app.model.User
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Test

internal class QnaServiceTest {

    private lateinit var qnaService: QnaService
    private lateinit var questionDao: QuestionDao
    private lateinit var answerDao: AnswerDao


    @BeforeEach
    fun setup() {
        questionDao = MockQuestionDao()
        answerDao = MockAnswerDao()
        qnaService = QnaService(questionDao, answerDao)
    }

    @Test
    fun deleteQuestion_없는_질문() {
        assertThrows(CannotDeleteException::class.java) {
            qnaService.deleteQuestion(1L, newUser("userId"))
        }

    }

    @Test
    fun deleteQuestion_다른_사용자() {
        assertThrows(CannotDeleteException::class.java) {
            var question = newQuestion(1L, "keeseung");
            questionDao.insert(question)
            qnaService.deleteQuestion(1L, newUser("userId"))
        }

    }

    @Test
    fun deleteQuestion_같은_사용자_답변없음() {
        var question = newQuestion(1L, "keeseung");
        questionDao.insert(question)
        qnaService.deleteQuestion(1L, newUser("keeseung"))
    }

    private fun newUser(userId: String): User {
        return User(userId, null, userId, null)
    }

    private fun newQuestion(questionId: Long, userId: String): Question {
        return Question(questionId, title = "Test", contents = "content Test", writer = userId)
    }

}