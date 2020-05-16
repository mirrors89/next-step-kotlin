package core.nmvc

import core.mvc.JsonView
import core.mvc.JspView
import core.mvc.ModelAndView

abstract class AbstractNewController {

    protected fun jspView(forwardUrl: String): ModelAndView {
        return ModelAndView(JspView(forwardUrl))
    }

    protected fun jsonView(): ModelAndView {
        return ModelAndView(JsonView())
    }
}