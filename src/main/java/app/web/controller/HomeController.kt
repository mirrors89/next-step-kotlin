package app.web.controller

import app.dao.QuestionDao
import core.mvc.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class HomeController : AbstractController() {

    private val questionDao = QuestionDao()

    override fun execute(req: HttpServletRequest, resp: HttpServletResponse): ModelAndView {
        val findAll = questionDao.findAll()

        return jspView("/home.jsp")
                .addObject("questions", findAll)
    }

}
