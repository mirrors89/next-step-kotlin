package webserver.controller

import webserver.http.HttpRequest
import webserver.http.HttpResponse

interface Controller {
    fun service(httpRequest: HttpRequest, httpResponse: HttpResponse)
}