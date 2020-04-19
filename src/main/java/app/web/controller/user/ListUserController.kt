package app.web.controller.user

import app.dao.UserDao
import app.web.controller.UserSessionUtils
import core.mvc.Controller
import core.db.DataBase
import core.mvc.JspView
import core.mvc.View
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class ListUserController: Controller {
    override fun execute(req: HttpServletRequest, resp: HttpServletResponse): View {
        if(!UserSessionUtils.isLogined(req.session)) {
            return JspView("redirect:/users/loginForm")
        }

        val userDao = UserDao()
        req.setAttribute("users", userDao.findAll())
        return JspView("/user/list.jsp")
    }
}