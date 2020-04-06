package web.controller

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class HomeController : Controller {
    override fun execute(req: HttpServletRequest, resp: HttpServletResponse): String {
        return "/index.html"
    }

}
