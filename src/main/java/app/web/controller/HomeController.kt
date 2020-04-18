package app.web.controller

import app.dao.QuestionDao
import core.mvc.Controller
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class HomeController : Controller {
    override fun execute(req: HttpServletRequest, resp: HttpServletResponse): String {
        val questionDao = QuestionDao()
        val findAll = questionDao.findAll()
        req.setAttribute("questions", findAll)
        return "/home.jsp"
    }

}
