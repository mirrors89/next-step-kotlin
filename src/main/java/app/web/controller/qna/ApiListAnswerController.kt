package app.web.controller.qna

import app.dao.QuestionDao
import core.mvc.AbstractController
import core.mvc.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class ApiListAnswerController: AbstractController() {

    private val questionDao = QuestionDao.getInstance()

    override fun execute(req: HttpServletRequest, resp: HttpServletResponse): ModelAndView {
        return jsonView().addObject("questions", questionDao.findAll())
    }


}