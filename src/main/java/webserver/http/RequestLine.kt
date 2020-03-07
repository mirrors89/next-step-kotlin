package webserver.http

data class RequestLine(val method: HttpMethod,
                  val requestURL: RequestURL,
                  val protocol: String) {

    companion object {
        private const val METHOD_INDEX = 0
        private const val METHOD_URL = 1
        private const val METHOD_PROTOCOL = 2
        private const val SPACE_STRING = " "

        fun parse(requestLine: String): RequestLine {
            val requestLineSplit = requestLine.split(SPACE_STRING).toTypedArray()
            val method = HttpMethod.find(requestLineSplit[METHOD_INDEX])
            val requestURL = RequestURL.parse(requestLineSplit[METHOD_URL])
            return RequestLine(method, requestURL, requestLineSplit[METHOD_PROTOCOL])
        }

    }

    fun getQueryValue(key: String): String? = requestURL.parameter.get(key)


    fun getParameters(): Map<String, String> {
        return requestURL.getParameters()
    }

    fun getPath(): String = requestURL.path
}