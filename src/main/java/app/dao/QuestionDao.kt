package app.dao

import app.model.Question

interface QuestionDao {

    fun insert(question: Question)

    fun findById(questionId: Long): Question?

    fun findAll(): List<Question>

    fun update(question: Question)

    fun incrementCountOfAnswer(questionId: Long)

    fun delete(questionId: Long)

}