package app.web.controller.qna

import app.dao.AnswerDao
import app.dao.QuestionDao
import core.mvc.Controller
import core.mvc.JspView
import core.mvc.View
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class ShowController : Controller {
    override fun execute(req: HttpServletRequest, resp: HttpServletResponse): View {
        val questionId = req.getParameter("questionId").toLong()
        val questionDao = QuestionDao()
        val answerDao = AnswerDao()
        req.setAttribute("question", questionDao.findById(questionId))
        req.setAttribute("answers", answerDao.findAllByQuestionId(questionId))

        return JspView("/qna/show.jsp")
    }
}