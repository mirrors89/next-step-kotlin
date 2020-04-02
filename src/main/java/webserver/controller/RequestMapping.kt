package webserver.controller

class RequestMapping {
    companion object {
        private val controllers: HashMap<String, Controller> = hashMapOf()

        fun getController(requestUrl: String): Controller? {
            return controllers[requestUrl]
        }
    }

    init {
        controllers["/user/create"] = CreateUserController()
        controllers["/user/login"] = LoginController()
        controllers["/user/list"] = ListUserController()
    }
}