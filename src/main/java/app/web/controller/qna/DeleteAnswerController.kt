package app.web.controller.qna

import app.dao.AnswerDao
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import app.model.Result
import core.mvc.*

class DeleteAnswerController : AbstractController() {
    override fun execute(req: HttpServletRequest, resp: HttpServletResponse): ModelAndView {
        val answerId = req.getParameter("answerId")
        val answerDao = AnswerDao()

        answerDao.delete(answerId)

        val result = Result.ok()
        return jsonView()
                .addObject("status", result.isStatus)
                .addObject("message", result.message)
    }

}
