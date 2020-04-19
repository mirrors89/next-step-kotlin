package core.mvc

import java.lang.NullPointerException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JspView : View {
    companion object {
        private const val DEFAULT_REDIRECT_PREFIX = "redirect:"
    }

    private lateinit var viewName: String

    constructor(viewName: String?) {
        viewName ?: throw NullPointerException("viewName is null. 이동할 URL을 추가해 주세요.")

        this.viewName = viewName
    }

    @Throws(Exception::class)
    override fun render(request: HttpServletRequest, response: HttpServletResponse) {
        if(viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
            response.sendRedirect(viewName.substring(DEFAULT_REDIRECT_PREFIX.length))
            return
        }

        val rd = request.getRequestDispatcher(viewName)
        rd.forward(request, response)
    }
}