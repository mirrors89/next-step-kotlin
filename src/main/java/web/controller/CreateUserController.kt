package web.controller

import db.DataBase
import model.User
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CreateUserController: Controller {
    override fun execute(req: HttpServletRequest, resp: HttpServletResponse): String {

        val user = User(req.getParameter("userId"),
                req.getParameter("password"),
                req.getParameter("name"),
                req.getParameter("email"))
        DataBase.addUser(user)

        val session = req.session
        session.setAttribute("user", user)
        return "redirect:/users"
    }
}