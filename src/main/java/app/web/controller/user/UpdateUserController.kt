package app.web.controller.user

import app.dao.UserDao
import core.mvc.Controller
import core.db.DataBase
import core.db.DataBase.findUserById
import app.model.User
import app.web.controller.UserSessionUtils
import core.mvc.JspView
import core.mvc.View
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class UpdateUserController : Controller {

    override fun execute(req: HttpServletRequest, resp: HttpServletResponse): View {
        val userDao = UserDao()
        val user = userDao.findByUserId(req.getParameter("userId"))
        check(UserSessionUtils.isSameUser(req.session, user)) { "다른 사용자의 정보를 수정할 수 없습니다." }

        val updateUser = User(req.getParameter("userId"),
                req.getParameter("password"),
                req.getParameter("name"),
                req.getParameter("email"))

        DataBase.addUser(updateUser)
        return JspView("redirect:/users")
    }
}