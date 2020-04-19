package core.mvc

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

interface Controller {
    fun execute(req: HttpServletRequest, resp: HttpServletResponse): View
}