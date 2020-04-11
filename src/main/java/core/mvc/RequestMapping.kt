package core.mvc

import app.web.controller.*
import org.slf4j.LoggerFactory

class RequestMapping {
    companion object {
        private val logger = LoggerFactory.getLogger(RequestMapping::class.java)
    }
    private val mapping: HashMap<String, Controller> = hashMapOf()

    fun initMapping() {
        mapping["/"] = HomeController()
        mapping["/users/form"] = ForwardController("/user/form.jsp")
        mapping["/user/loginForm"] = ForwardController("/user/login.jsp")
        mapping["/users"] = ListUserController()
        mapping["/user/login"] = LoginController()
        mapping["/user/profile"] = ProfileController()
        mapping["/user/logout"] = LogoutController()
        mapping["/user/create"] = CreateUserController()
        mapping["/user/updateForm"] = UpdateFormUserController()
        mapping["/user/update"] = UpdateUserController()

        logger.info("Initialized Request Mapping!")
    }

    fun findController(requestUri: String): Controller? {
        return mapping[requestUri]
    }
}