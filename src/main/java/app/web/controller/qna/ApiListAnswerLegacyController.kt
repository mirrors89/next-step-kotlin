package app.web.controller.qna

import app.dao.QuestionDao
import core.mvc.AbstractLegacyController
import core.mvc.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class ApiListAnswerLegacyController(private val questionDao: QuestionDao) : AbstractLegacyController() {

    override fun execute(req: HttpServletRequest, resp: HttpServletResponse): ModelAndView {
        return jsonView().addObject("questions", questionDao.findAll())
    }


}