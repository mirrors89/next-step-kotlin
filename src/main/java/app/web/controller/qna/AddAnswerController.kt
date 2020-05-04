package app.web.controller.qna

import app.dao.AnswerDao
import app.dao.QuestionDao
import app.model.Answer
import app.service.QnaService
import core.mvc.AbstractController
import core.mvc.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AddAnswerController : AbstractController() {

    private val questionDao = QuestionDao.getInstance()
    private val answerDao = AnswerDao.getInstance()

    override fun execute(req: HttpServletRequest, resp: HttpServletResponse): ModelAndView {
        val questionId = req.getParameter("questionId").toLong()

        val answer = Answer(questionId,
                req.getParameter("writer"),
                req.getParameter("contents"))

        val savedAnswer = answerDao.insert(answer)
        questionDao.incrementCountOfAnswer(questionId)
        return jsonView().addObject("answer", savedAnswer)
    }
}