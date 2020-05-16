package core.mvc

import java.lang.NullPointerException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class ForwardLegacyController(private val forwardUrl: String) : AbstractLegacyController() {

    init {
        if(forwardUrl.isEmpty()) {
            throw NullPointerException("forwardUrl is null. 이동할 URL을 입력하세요.")
        }
    }


    override fun execute(req: HttpServletRequest, resp: HttpServletResponse): ModelAndView {
        return jspView(forwardUrl)

    }

}
