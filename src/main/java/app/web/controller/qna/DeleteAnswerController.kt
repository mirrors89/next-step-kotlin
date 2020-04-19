package app.web.controller.qna

import app.dao.AnswerDao
import core.mvc.Controller
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import app.model.Result
import core.mvc.JsonView
import core.mvc.View

class DeleteAnswerController : Controller {
    override fun execute(req: HttpServletRequest, resp: HttpServletResponse): View {
        val answerId = req.getParameter("answerId")
        val answerDao = AnswerDao()

        answerDao.delete(answerId)

        val ok = Result.ok()
        req.setAttribute("status", ok.isStatus)
        req.setAttribute("message", ok.message)

        return JsonView()
    }

}
