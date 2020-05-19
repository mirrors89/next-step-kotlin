package core.mvc

import app.dao.impl.JdbcAnswerDao
import app.dao.impl.JdbcQuestionDao
import app.service.QnaService
import app.web.controller.*
import app.web.controller.qna.*
import core.nmvc.HandlerMapping
import org.slf4j.LoggerFactory
import javax.servlet.http.HttpServletRequest

class LegacyHandlerMapping: HandlerMapping {
    companion object {
        private val logger = LoggerFactory.getLogger(LegacyHandlerMapping::class.java)
    }
    private val mapping: HashMap<String, LegacyController> = hashMapOf()

    fun initMapping() {
        val questionDao = JdbcQuestionDao()
        val answerDao = JdbcAnswerDao()
        val qnaService = QnaService(questionDao, answerDao)

        mapping["/"] = HomeLegacyController(questionDao)

        mapping["/qna/show"] = ShowLegacyController(questionDao, answerDao)
        mapping["/qna/form"] = FormLegacyController()
        mapping["/qna/create"] = CreateQuestionLegacyController(questionDao)
        mapping["/qna/updateForm"] = UpdateFormLegacyController(questionDao)
        mapping["/qna/update"] = UpdateQuestionLegacyController(questionDao)
        mapping["/qna/delete"] = DeleteQuestionLegacyController(qnaService)

        mapping["/api/qna/list"] = ApiListAnswerLegacyController(questionDao)
        mapping["/api/qna/delete"] = ApiDeleteQuestionLegacyController(qnaService)
        mapping["/api/qna/addAnswer"] = AddAnswerLegacyController(questionDao, answerDao)
        mapping["/api/qna/deleteAnswer"] = ApiDeleteAnswerLegacyController(answerDao)

        logger.info("Initialized Request Mapping!")
    }

    override fun getHandler(request: HttpServletRequest): LegacyController? {
        return mapping[request.requestURI]
    }
}