package webserver

import webserver.controller.RequestMapping
import webserver.http.HttpRequest
import webserver.http.HttpResponse
import java.net.Socket

class RequestHandler(connectionSocket: Socket) : Thread() {
    private var connection: Socket = connectionSocket


    override fun run() {
        val inputStream = connection.getInputStream()
        val outputStream = connection.getOutputStream()

        inputStream.use { i ->
            outputStream.use { o ->

                val httpRequest = HttpRequest(i)
                val httpResponse = HttpResponse(o)

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