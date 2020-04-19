package app.web.controller.qna

import app.dao.AnswerDao
import app.dao.QuestionDao
import core.mvc.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class ShowController: AbstractController() {
    override fun execute(req: HttpServletRequest, resp: HttpServletResponse): ModelAndView {
        val questionId = req.getParameter("questionId").toLong()
        val questionDao = QuestionDao()
        val answerDao = AnswerDao()

        val findById = questionDao.findById(questionId)
        val attributeValue = answerDao.findAllByQuestionId(questionId)
        return jspView("/qna/show.jsp")
                .addObject("question", findById)
                .addObject("answers", attributeValue)
    }
}