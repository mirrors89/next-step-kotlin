package app.web.controller

import app.dao.QuestionDao
import core.mvc.Controller
import core.mvc.JspView
import core.mvc.View
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class HomeController : Controller {
    override fun execute(req: HttpServletRequest, resp: HttpServletResponse): View {
        val questionDao = QuestionDao()
        val findAll = questionDao.findAll()
        req.setAttribute("questions", findAll)
        return JspView("/home.jsp")
    }

}
