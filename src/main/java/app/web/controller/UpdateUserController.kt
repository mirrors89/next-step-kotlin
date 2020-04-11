package app.web.controller

import core.mvc.Controller
import core.db.DataBase
import core.db.DataBase.findUserById
import app.model.User
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class UpdateUserController : Controller {

    override fun execute(req: HttpServletRequest, resp: HttpServletResponse): String {
        val user = findUserById(req.getParameter("userId"))
        check(UserSessionUtils.isSameUser(req.session, user)) { "다른 사용자의 정보를 수정할 수 없습니다." }

        val updateUser = User(req.getParameter("userId"),
                req.getParameter("password"),
                req.getParameter("name"),
                req.getParameter("email"))

        DataBase.addUser(updateUser)
        return "redirect:/users"
    }
}