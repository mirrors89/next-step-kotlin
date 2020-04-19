package app.web.controller.qna

import app.dao.AnswerDao
import app.model.Answer
import com.fasterxml.jackson.databind.ObjectMapper
import core.mvc.Controller
import core.mvc.JsonView
import core.mvc.View
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AddAnswerController : Controller {


    override fun execute(req: HttpServletRequest, resp: HttpServletResponse): View {
        val answer = Answer( req.getParameter("questionId").toLong(),
                req.getParameter("writer"),
                req.getParameter("contents"))

        val answerDao = AnswerDao()

        val savedAnswer = answerDao.insert(answer)
        req.setAttribute("answer", savedAnswer)
        return JsonView()
    }
}