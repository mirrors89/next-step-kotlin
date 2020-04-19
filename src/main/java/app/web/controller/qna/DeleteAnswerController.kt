package app.web.controller.qna

import app.dao.AnswerDao
import core.mvc.Controller
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import app.model.Result
import com.fasterxml.jackson.databind.ObjectMapper

class DeleteAnswerController : Controller {
    override fun execute(req: HttpServletRequest, resp: HttpServletResponse): String? {
        val answerId = req.getParameter("answerId")

        val answerDao = AnswerDao()

        val mapper = ObjectMapper()
        resp.contentType = "application/json;charset=UTF-8"
        val out = resp.writer

        answerDao.delete(answerId)
        out.print(mapper.writeValueAsString(Result.ok()))
        return null
    }

}
