package app.web.controller

import app.dao.impl.JdbcQuestionDao
import core.mvc.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class HomeLegacyController : AbstractLegacyController() {

    private val questionDao = JdbcQuestionDao()

    override fun execute(req: HttpServletRequest, resp: HttpServletResponse): ModelAndView {
        val findAll = questionDao.findAll()

        return jspView("/home.jsp")
                .addObject("questions", findAll)
    }

}
