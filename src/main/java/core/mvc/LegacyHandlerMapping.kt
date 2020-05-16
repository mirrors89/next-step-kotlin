package core.mvc

import app.web.controller.*
import app.web.controller.qna.*
import app.web.controller.user.*
import core.nmvc.HandlerMapping
import org.slf4j.LoggerFactory
import javax.servlet.http.HttpServletRequest

class LegacyHandlerMapping: HandlerMapping {
    companion object {
        private val logger = LoggerFactory.getLogger(LegacyHandlerMapping::class.java)
    }
    private val mapping: HashMap<String, LegacyController> = hashMapOf()

    fun initMapping() {
        mapping["/"] = HomeLegacyController()

        mapping["/qna/show"] = ShowLegacyController()
        mapping["/qna/form"] = FormLegacyController()
        mapping["/qna/create"] = CreateQuestionLegacyController()
        mapping["/qna/updateForm"] = UpdateFormLegacyController()
        mapping["/qna/update"] = UpdateQuestionLegacyController()
        mapping["/qna/delete"] = DeleteQuestionLegacyController()

        mapping["/api/qna/list"] = ApiListAnswerLegacyController()
        mapping["/api/qna/delete"] = ApiDeleteQuestionLegacyController()
        mapping["/api/qna/addAnswer"] = AddAnswerLegacyController()
        mapping["/api/qna/deleteAnswer"] = ApiDeleteAnswerLegacyController()

        logger.info("Initialized Request Mapping!")
    }

    override fun getHandler(request: HttpServletRequest): LegacyController? {
        return mapping[request.requestURI]
    }
}