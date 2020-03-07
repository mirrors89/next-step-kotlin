package webserver.http

data class RequestURL(val path: String, val parameter: Parameter) {

    constructor(path: String) : this(path, Parameter())

    companion object {
        private const val PATH_SEPARATOR = "?"

        fun parse(urlString: String): RequestURL {
            var path = urlString
            val q = urlString.lastIndexOf(PATH_SEPARATOR)
            if (q > -1) {
                path = urlString.substring(0, q)
                val querySting = urlString.substring(q + 1)
                val parameter: Parameter = Parameter.parse(querySting)
                return RequestURL(path, parameter)
            }
            return RequestURL(path)
        }
    }

    fun getParameters(): Map<String, String> {
        return parameter.parameter
    }

    fun getParameter(key: String): String? {
        return parameter.get(key)
    }
}
