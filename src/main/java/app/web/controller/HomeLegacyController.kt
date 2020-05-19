package app.web.controller

import app.dao.QuestionDao
import app.dao.impl.JdbcQuestionDao
import core.mvc.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class HomeLegacyController(private val questionDao: QuestionDao) : AbstractLegacyController() {

    override fun execute(req: HttpServletRequest, resp: HttpServletResponse): ModelAndView {
        val findAll = questionDao.findAll()

        return jspView("/home.jsp")
                .addObject("questions", findAll)
    }

}
