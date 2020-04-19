package app.web.controller.user

import core.mvc.Controller
import core.mvc.JspView
import core.mvc.View
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class LogoutController : Controller {
    override fun execute(req: HttpServletRequest, resp: HttpServletResponse): View {
        val session = req.session
        session.invalidate()

        return JspView("redirect:/")
    }

}