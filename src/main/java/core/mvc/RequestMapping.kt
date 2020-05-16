package core.mvc

import app.web.controller.*
import app.web.controller.qna.*
import app.web.controller.user.*
import org.slf4j.LoggerFactory

class RequestMapping {
    companion object {
        private val logger = LoggerFactory.getLogger(RequestMapping::class.java)
    }
    private val mapping: HashMap<String, LegacyController> = hashMapOf()

    fun initMapping() {
        mapping["/"] = HomeLegacyController()
        mapping["/users/form"] = ForwardLegacyController("/user/form.jsp")
        mapping["/user/loginForm"] = ForwardLegacyController("/user/login.jsp")
        mapping["/users"] = ListUserLegacyController()
        mapping["/user/login"] = LoginLegacyController()
        mapping["/user/profile"] = ProfileLegacyController()
        mapping["/user/logout"] = LogoutLegacyController()
        mapping["/user/create"] = CreateUserLegacyController()
        mapping["/user/updateForm"] = UpdateFormUserLegacyController()
        mapping["/user/update"] = UpdateUserLegacyController()

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

    fun findController(requestUri: String): LegacyController? {
        return mapping[requestUri]
    }
}