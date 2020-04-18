package app.web.controller.qna

import app.dao.AnswerDao
import app.model.Answer
import com.fasterxml.jackson.databind.ObjectMapper
import core.mvc.Controller
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AddAnswerController : Controller {


    override fun execute(req: HttpServletRequest, resp: HttpServletResponse): String? {
        val answer = Answer( req.getParameter("questionId").toLong(),
                req.getParameter("writer"),
                req.getParameter("contents"))

        val answerDao = AnswerDao()

        val savedAnswer = answerDao.insert(answer)
        val mapper = ObjectMapper()
        resp.contentType = "application/json;charset=UTF-8"
        val out = resp.writer
        out.print(mapper.writeValueAsString(savedAnswer))

        return null
    }
}