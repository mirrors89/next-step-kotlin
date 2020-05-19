package app.web.controller.qna

import app.dao.impl.JdbcAnswerDao
import app.model.Result
import core.mvc.AbstractLegacyController
import core.mvc.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class ApiDeleteAnswerLegacyController : AbstractLegacyController() {

    private val answerDao = JdbcAnswerDao.getInstance()

    override fun execute(req: HttpServletRequest, resp: HttpServletResponse): ModelAndView {
        val answerId = req.getParameter("answerId") as Long

        answerDao.delete(answerId)

        val result = Result.ok()
        return jsonView()
                .addObject("status", result.isStatus)
                .addObject("message", result.message)
    }

}
