package core.nmvc

import core.annotation.Controller
import core.annotation.RequestMapping
import core.annotation.RequestMethod
import core.mvc.JspView
import core.mvc.ModelAndView
import org.slf4j.LoggerFactory
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
class MyController {

    @RequestMapping("/users/findUserId")
    fun findUserId(request: HttpServletRequest, response: HttpServletResponse): ModelAndView? {
        log.debug("findUserId")

        return null
    }

//    @RequestMapping(value = "/users", method = RequestMethod.POST)
//    fun save(request: HttpServletRequest, response: HttpServletResponse): ModelAndView? {
//        log.debug("save")
//
//        return null
//    }

    @RequestMapping("/users")
    fun list(request: HttpServletRequest, response: HttpServletResponse): ModelAndView {
        log.debug("list")
        return ModelAndView(JspView("/users/list.jsp"))
    }

    @RequestMapping(value = "/users/show", method = RequestMethod.GET)
    fun show(request: HttpServletRequest, response: HttpServletResponse): ModelAndView {
        log.debug("list")
        return ModelAndView(JspView("/users/show.jsp"))
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    fun create(request: HttpServletRequest, response: HttpServletResponse): ModelAndView {
        log.debug("create")
        return ModelAndView(JspView("redirect:/users"))
    }

    companion object {
        private val log = LoggerFactory.getLogger(MyController::class.java)
    }
}