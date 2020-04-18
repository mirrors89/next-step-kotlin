package app.web.controller.qna

import app.dao.AnswerDao
import app.dao.QuestionDao
import core.mvc.Controller
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class ShowController : Controller {
    override fun execute(req: HttpServletRequest, resp: HttpServletResponse): String {
        val questionId = req.getParameter("questionId").toLong()
        val questionDao = QuestionDao()
        val answerDao = AnswerDao()
        req.setAttribute("question", questionDao.findById(questionId))
        req.setAttribute("answers", answerDao.findAllByQuestionId(questionId))

        return "/qna/show.jsp"
    }
}