package app.web.controller.user

import app.dao.UserDao
import app.web.controller.UserSessionUtils
import core.mvc.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class ListUserLegacyController: AbstractLegacyController() {

    private val userDao = UserDao.getInstance()

    override fun execute(req: HttpServletRequest, resp: HttpServletResponse): ModelAndView {
        if(!UserSessionUtils.isLogined(req.session)) {
            return jspView("redirect:/users/loginForm")
        }


        return jspView("/user/list.jsp")
                .addObject("users", userDao.findAll())
    }
}