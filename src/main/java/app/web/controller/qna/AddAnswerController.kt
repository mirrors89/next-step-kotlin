package app.web.controller.qna

import app.dao.AnswerDao
import app.model.Answer
import core.mvc.AbstractController
import core.mvc.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AddAnswerController : AbstractController() {

    override fun execute(req: HttpServletRequest, resp: HttpServletResponse): ModelAndView {
        val answer = Answer( req.getParameter("questionId").toLong(),
                req.getParameter("writer"),
                req.getParameter("contents"))

        val answerDao = AnswerDao()

        val savedAnswer = answerDao.insert(answer)
        return jsonView().addObject("answer", savedAnswer)
    }
}