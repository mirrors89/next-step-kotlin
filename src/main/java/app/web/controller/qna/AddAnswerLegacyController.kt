package app.web.controller.qna

import app.dao.impl.JdbcAnswerDao
import app.dao.impl.JdbcQuestionDao
import app.model.Answer
import core.mvc.AbstractLegacyController
import core.mvc.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AddAnswerLegacyController : AbstractLegacyController() {

    private val questionDao = JdbcQuestionDao.getInstance()
    private val answerDao = JdbcAnswerDao.getInstance()

    override fun execute(req: HttpServletRequest, resp: HttpServletResponse): ModelAndView {
        val questionId = req.getParameter("questionId").toLong()

        val answer = Answer(questionId,
                req.getParameter("writer"),
                req.getParameter("contents"))

        val savedAnswer = answerDao.insert(answer)
        questionDao.incrementCountOfAnswer(questionId)
        return jsonView().addObject("answer", savedAnswer)
    }
}