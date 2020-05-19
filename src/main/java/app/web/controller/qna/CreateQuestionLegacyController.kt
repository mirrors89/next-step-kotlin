package app.web.controller.qna

import app.dao.impl.JdbcQuestionDao
import app.model.Question
import app.web.controller.UserSessionUtils
import core.mvc.AbstractLegacyController
import core.mvc.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CreateQuestionLegacyController: AbstractLegacyController() {

    private val questionDao = JdbcQuestionDao.getInstance()

    override fun execute(req: HttpServletRequest, resp: HttpServletResponse): ModelAndView {
        check(UserSessionUtils.isLogined(req.session)) {
            return jspView("redirect:/user/loginForm")
        }

        val writer = req.getParameter("writer")
        val title = req.getParameter("title")
        val contents = req.getParameter("contents")

        val newQuestion = Question(writer, title, contents)
        questionDao.insert(newQuestion)

        return jspView("redirect:/")

    }
}