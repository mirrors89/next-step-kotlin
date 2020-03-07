package webserver.http

data class RequestLine(val method: String,
                  val requestURL: String,
                  val protocol: String) {

    companion object {
        private const val METHOD_INDEX = 0
        private const val METHOD_URL = 1
        private const val METHOD_PROTOCOL = 2
        private const val SPACE_STRING = " "

        fun parse(requestLine: String): RequestLine {
            val requestLineSplit = requestLine.split(SPACE_STRING).toTypedArray()
            val method = requestLineSplit[METHOD_INDEX]
            val requestURL = requestLineSplit[METHOD_URL]
            return RequestLine(method, requestURL, requestLineSplit[METHOD_PROTOCOL])
        }

    }
}