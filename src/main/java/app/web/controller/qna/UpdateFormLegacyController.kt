package app.web.controller.qna

import app.dao.QuestionDao
import app.web.controller.UserSessionUtils
import core.mvc.AbstractLegacyController
import core.mvc.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class UpdateFormLegacyController(private val questionDao: QuestionDao) : AbstractLegacyController() {

    override fun execute(req: HttpServletRequest, resp: HttpServletResponse): ModelAndView {
        check(UserSessionUtils.isLogined(req.session)) {
            return jspView("redirect:/user/loginForm")
        }

        val questionId = req.getParameter("questionId").toLong()
        val question = questionDao.findById(questionId)
        check(question!!.isSameUser(UserSessionUtils.getUserFromSession(req.session))) {
            throw IllegalStateException("다른 사용자가 쓴 글을 수정할 수 없습니다.")
        }

        return jspView("/qna/update.jsp")
                .addObject("question", question)

    }
}