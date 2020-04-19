package core.mvc

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

interface View {
    @Throws(Exception::class)
    fun render(request: HttpServletRequest, response: HttpServletResponse)
}