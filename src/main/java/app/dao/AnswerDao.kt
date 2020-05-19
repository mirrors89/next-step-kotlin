package app.dao

import app.model.Answer

interface AnswerDao {
    fun insert(answer: Answer) : Answer?

    fun findById(answerId: Long): Answer?

    fun findAllByQuestionId(questionId: Long): List<Answer>

    fun delete(answerId: Long)
}