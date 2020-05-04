package app.web.controller.user

import app.dao.UserDao
import core.db.DataBase
import app.model.User
import core.mvc.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CreateUserController: AbstractController() {

    override fun execute(req: HttpServletRequest, resp: HttpServletResponse): ModelAndView {

        val user = User(req.getParameter("userId"),
                req.getParameter("password"),
                req.getParameter("name"),
                req.getParameter("email"))

        val userDao = UserDao()
        userDao.insert(user)

        val session = req.session
        session.setAttribute("user", user)
        return jspView("redirect:/users")
    }
}