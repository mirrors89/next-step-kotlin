package web.controller

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

interface Controller {
    fun execute(req: HttpServletRequest, resp: HttpServletResponse): String
}