package app.model

import app.CannotDeleteException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class QuestionTest {

    @Test
    fun canDelete_글쓴이_다름() {
        assertThrows(CannotDeleteException::class.java) {
            val user = newUser("keeseung")
            val question = newQuestion(1L, "user")

            question.canDelete(user, listOf())
        }
    }


    @Test
    fun canDelete_글쓴이_같음_답변_없음() {
        val user = newUser("keeseung")
        val question = newQuestion(1L, "keeseung")

        assertTrue(question.canDelete(user, listOf()))

    }


    @Test
    fun canDelete_같은_사용자_답변() {
        val userId = "keeseung"
        val user = newUser(userId)
        val question = newQuestion(1L, userId)
        val answers = listOf(newAnswer(userId))

        assertTrue(question.canDelete(user, answers))
    }



    @Test
    fun canDelete_다른_사용자_답변() {
        assertThrows(CannotDeleteException::class.java) {
            val user = newUser("keeseung")
            val answers = listOf(newAnswer("keeseung"), newAnswer("user"))

            newQuestion(1L, "user").canDelete(user, answers)
        }
    }

    private fun newUser(userId: String): User {
        return User(userId, null, userId, null)
    }

    private fun newQuestion(questionId: Long, userId: String): Question {
        return Question(questionId, title = "Test", contents = "content Test", writer = userId)
    }

    private fun newAnswer(userId: String): Answer {
        return Answer(questionId = 1L, writer = userId, contents = "TEST")
    }
}