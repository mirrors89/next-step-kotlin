package app.service

import app.CannotDeleteException
import app.dao.AnswerDao
import app.dao.QuestionDao
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
    fun deleteQuestion(questionId: Long, user: User?) {
        val question = questionDao.findById(questionId) ?: throw CannotDeleteException("존재하지 않는 질문입니다.")
        if (!question.isSameUser(user)) {
            throw CannotDeleteException("다른 사용자가 쓴 글을 삭제할 수 없습니다.")
        }

        val answers = answerDao.findAllByQuestionId(questionId)
        if (answers.isEmpty()) {
            questionDao.delete(questionId)
            return
        }


        for(answer in answers) {
            if(!question.isSameUser(answer.writer)) {
                throw CannotDeleteException("다른 사용자가 추가한 댓글이 존재해 삭제할 수 없습니다.")
            }
        }

        questionDao.delete(questionId)
    }

    companion object {
        private var QNA_SERVICE: QnaService? = null

        fun getInstance(questionDao: QuestionDao, answerDao: AnswerDao): QnaService {
            if(QNA_SERVICE == null) {
                QNA_SERVICE = QnaService(questionDao, answerDao)
            }
            return QNA_SERVICE!!
        }
    }
}