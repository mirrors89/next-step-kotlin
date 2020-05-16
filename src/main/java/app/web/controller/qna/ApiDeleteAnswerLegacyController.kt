package app.web.controller.qna

import app.dao.AnswerDao
import app.model.Result
import core.mvc.AbstractLegacyController
import core.mvc.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class ApiDeleteAnswerLegacyController : AbstractLegacyController() {

    private val answerDao = AnswerDao.getInstance()

    override fun execute(req: HttpServletRequest, resp: HttpServletResponse): ModelAndView {
        val answerId = req.getParameter("answerId")

        answerDao.delete(answerId)

        val result = Result.ok()
        return jsonView()
                .addObject("status", result.isStatus)
                .addObject("message", result.message)
    }

}
