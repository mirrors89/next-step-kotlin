package app.web.controller

import app.dao.UserDao
import core.mvc.Controller
import core.db.DataBase
import app.model.User
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CreateUserController: Controller {
    override fun execute(req: HttpServletRequest, resp: HttpServletResponse): String {

        val user = User(req.getParameter("userId"),
                req.getParameter("password"),
                req.getParameter("name"),
                req.getParameter("email"))

        val userDao = UserDao()
        userDao.insert(user)

        val session = req.session
        session.setAttribute("user", user)
        return "redirect:/users"
    }
}