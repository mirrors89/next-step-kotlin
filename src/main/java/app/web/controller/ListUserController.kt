package app.web.controller

import core.mvc.Controller
import core.db.DataBase
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class ListUserController: Controller {
    override fun execute(req: HttpServletRequest, resp: HttpServletResponse): String {
        if(!UserSessionUtils.isLogined(req.session)) {
            return "redirect:/users/loginForm"
        }

        req.setAttribute("users", DataBase.findAll())
        return "/user/list.jsp"
    }
}