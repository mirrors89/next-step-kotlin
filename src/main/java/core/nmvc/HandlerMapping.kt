package core.nmvc

import javax.servlet.http.HttpServletRequest

interface HandlerMapping {
    fun getHandler(request: HttpServletRequest): Any?
}