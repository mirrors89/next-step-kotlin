package app.web.controller.qna

import app.service.QnaService
import app.web.controller.UserSessionUtils
import core.mvc.AbstractController
import core.mvc.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class DeleteQuestionController: AbstractController() {

    private val qnaService = QnaService.getInstance()

    override fun execute(req: HttpServletRequest, resp: HttpServletResponse): ModelAndView {
        check(UserSessionUtils.isLogined(req.session)) {
            return jspView("redirect:/user/loginForm")
        }

        val questionId = req.getParameter("questionId").toLong()
        qnaService.deleteQuestion(questionId, UserSessionUtils.getUserFromSession(req.session))
        return jspView("redirect:/")

    }
}