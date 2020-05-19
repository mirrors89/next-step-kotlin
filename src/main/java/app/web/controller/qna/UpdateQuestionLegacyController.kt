package app.web.controller.qna

import app.dao.impl.JdbcQuestionDao
import app.model.Question
import app.web.controller.UserSessionUtils
import core.mvc.AbstractLegacyController
import core.mvc.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class UpdateQuestionLegacyController: AbstractLegacyController() {

    private val questionDao = JdbcQuestionDao.getInstance()

    override fun execute(req: HttpServletRequest, resp: HttpServletResponse): ModelAndView {
        check(UserSessionUtils.isLogined(req.session)) {
            return jspView("redirect:/user/loginForm")
        }

        val questionId = req.getParameter("questionId").toLong()
        val question = questionDao.findById(questionId)
        check(question!!.isSameUser(UserSessionUtils.getUserFromSession(req.session))) {
            throw IllegalStateException("다른 사용자가 쓴 글을 수정할 수 없습니다.")
        }

        val title = req.getParameter("title")
        val contents = req.getParameter("contents")

        question.update(Question(question.writer, title, contents))
        questionDao.update(question)

        return jspView("redirect:/")

    }
}