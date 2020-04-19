package core.mvc

abstract class AbstractController: Controller {
    protected fun jspView(forwardUrl: String): ModelAndView {
        return ModelAndView(JspView(forwardUrl))
    }

    protected fun jsonView(): ModelAndView {
        return ModelAndView(JsonView())
    }
}