package webserver.http

class RequestLine {

    private val method: HttpMethod
    private val path: String
    private val parameters: Parameters

    constructor(method: HttpMethod, path: String) : this(method, path, Parameters())

    constructor(method: HttpMethod, path: String, parameters: Parameters) {
        this.method = method
        this.path = path
        this.parameters = parameters
    }

    companion object {
        private const val METHOD_INDEX = 0
        private const val METHOD_URL = 1
        private const val PATH_SEPARATOR = "?"
        private const val SPACE_STRING = " "

        fun parse(line: String): RequestLine {
            val requestLineSplit = line.split(SPACE_STRING).toTypedArray()
            val method = HttpMethod.find(requestLineSplit[METHOD_INDEX])
            val urlString = requestLineSplit[METHOD_URL]

            if(HttpMethod.POST == method) {
                return RequestLine(method, urlString)
            }

            val q = urlString.lastIndexOf(PATH_SEPARATOR)
            if (q > -1) {
                val path = urlString.substring(0, q)
                val querySting = urlString.substring(q + 1)
                val parameters: Parameters = Parameters.parse(querySting)
                return RequestLine(method, path, parameters)
            }
            return RequestLine(method, urlString)
        }
    }

    fun getMethod() = method

    fun getPath(): String = path

    fun getParameters(): Parameters = parameters
}
