package webserver

import util.HttpRequestUtils
import webserver.controller.RequestMapping
import webserver.http.HttpRequest
import webserver.http.HttpResponse
import java.net.Socket
import java.util.*

class RequestHandler(connectionSocket: Socket) : Thread() {
    private var connection: Socket = connectionSocket


    override fun run() {
        val inputStream = connection.getInputStream()
        val outputStream = connection.getOutputStream()

        inputStream.use { i ->
            outputStream.use { o ->

                val httpRequest = HttpRequest(i)
                val httpResponse = HttpResponse(o)

                if(httpRequest.getCookie().getCookie("JSESSIONID") == null) {
                    httpResponse.addHeader("Set-Cookie", "JSESSIONID=" + UUID.randomUUID())
                }

                val controller = RequestMapping.getController(httpRequest.getPath())

                if(controller == null) {
                    val path = getDefaultPath(httpRequest.getPath())
                    httpResponse.forward(path)
                    return
                }

                controller.service(httpRequest, httpResponse)
            }
        }
    }

    private fun getDefaultPath(path: String): String {
        if(path.equals("/")) {
            return "/index.html"
        }

        return path
    }
}