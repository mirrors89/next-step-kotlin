package core.mvc

import java.lang.NullPointerException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JspView(private var viewName: String?) : View {
    companion object {
        private const val DEFAULT_REDIRECT_PREFIX = "redirect:"
    }

    init {
        if(viewName.isNullOrEmpty()) {
            throw NullPointerException("viewName is null. 이동할 URL을 추가해 주세요.")
        }
    }

    @Throws(Exception::class)
    override fun render(model: Map<String, Any?>, request: HttpServletRequest, response: HttpServletResponse) {
        if(viewName!!.startsWith(DEFAULT_REDIRECT_PREFIX)) {
            response.sendRedirect(viewName!!.substring(DEFAULT_REDIRECT_PREFIX.length))
            return
        }

        val keys = model.keys
        for(key in keys) {
            request.setAttribute(key, model[key])
        }

        val rd = request.getRequestDispatcher(viewName)
        rd.forward(request, response)
    }
}