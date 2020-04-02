package webserver.http

import util.IOUtils
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class HttpRequest(inputStream: InputStream) {

    private val headers: Headers
    private val requestLine: RequestLine
    private val parameters: Parameters

    init {
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        var line = bufferedReader.readLine()
        if(line.isNullOrEmpty()) {
        }

        val requestLine = RequestLine.parse(line)
        val headers = Headers()

        line = bufferedReader.readLine()
        while(!line.isNullOrEmpty()) {
            headers.put(line)
            line = bufferedReader.readLine()
        }
        if(HttpMethod.POST == requestLine.getMethod()) {
            val body = IOUtils.readData(bufferedReader, headers.getOrDefault("Content-Length").toInt())
            this.parameters = Parameters.parse(body)
        } else {
            this.parameters = requestLine.getParameters()
        }

        this.headers = headers
        this.requestLine = requestLine
    }

    fun getHeader(key: String): String? = headers.get(key)

    fun getMethod(): HttpMethod = requestLine.getMethod()

    fun getParameter(key: String): String? = parameters.get(key)

    fun getPath(): String = requestLine.getPath()
}