package app.service

import app.CannotDeleteException
import app.dao.AnswerDao
import app.dao.QuestionDao
import app.dao.impl.JdbcAnswerDao
import app.dao.impl.JdbcQuestionDao
import app.model.Answer

import app.model.Question
import app.model.User


class QnaService private constructor() {
    private lateinit var questionDao: QuestionDao
    private lateinit var answerDao: AnswerDao

    constructor(questionDao: QuestionDao, answerDao: AnswerDao): this() {
        this.questionDao = questionDao
        this.answerDao = answerDao
    }

    fun findById(questionId: Long): Question? {
        return questionDao.findById(questionId)
    }

    fun findAllByQuestionId(questionId: Long): List<Answer?>? {
        return answerDao.findAllByQuestionId(questionId)
    }

    @Throws(CannotDeleteException::class)
    fun deleteQuestion(questionId: Long, user: User) {
        val question = questionDao.findById(questionId) ?: throw CannotDeleteException("존재하지 않는 질문입니다.")

        val answers = answerDao.findAllByQuestionId(questionId)
        if(question.canDelete(user, answers)) {
            questionDao.delete(questionId)
        }
    }
}