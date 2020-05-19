package app.web.controller.qna

import app.dao.impl.JdbcAnswerDao
import app.dao.impl.JdbcQuestionDao
import core.mvc.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class ShowLegacyController: AbstractLegacyController() {

    private val questionDao = JdbcQuestionDao.getInstance()
    private val answerDao = JdbcAnswerDao.getInstance()

    override fun execute(req: HttpServletRequest, resp: HttpServletResponse): ModelAndView {
        val questionId = req.getParameter("questionId").toLong()

        val findById = questionDao.findById(questionId)
        val attributeValue = answerDao.findAllByQuestionId(questionId)
        return jspView("/qna/show.jsp")
                .addObject("question", findById)
                .addObject("answers", attributeValue)
    }
}