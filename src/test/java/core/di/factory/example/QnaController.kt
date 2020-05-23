package core.di.factory.example

import app.service.QnaService
import core.annotation.Controller
import core.annotation.Inject
import core.annotation.RequestMapping
import core.mvc.ModelAndView
import core.nmvc.AbstractNewController
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
class QnaController @Inject constructor(private val qnaService: MyQnaService) : AbstractNewController() {



    @RequestMapping("/questions")
    @Throws(Exception::class)
    fun list(request: HttpServletRequest?, response: HttpServletResponse?): ModelAndView {
        return jspView("/qna/list.jsp")
    }

    fun getQnaService(): MyQnaService? {
        return this.qnaService
    }

}