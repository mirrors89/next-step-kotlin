package webserver.controller

import webserver.http.HttpRequest
import webserver.http.HttpResponse

abstract class AbstractController: Controller {
    override fun service(httpRequest: HttpRequest, httpResponse: HttpResponse) {
        if(httpRequest.getMethod().isPost()) {
            doPost(httpRequest, httpResponse)
            return
        }
        doGet(httpRequest, httpResponse)
    }

    protected abstract fun doGet(httpRequest: HttpRequest, httpResponse: HttpResponse)

    protected abstract fun doPost(httpRequest: HttpRequest, httpResponse: HttpResponse)
}