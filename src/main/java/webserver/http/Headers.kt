package webserver.http

class Headers(val header: HashMap<String, String> = HashMap()) {

    companion object {
        private const val DEFAULT_STRING = ""
        private const val SPLIT_SEPARATOR = ":"
        private const val KEY = 0
        private const val VALUE = 1

    }

    fun put(headerString: String) {
        val headerKeyValue = headerString.split(SPLIT_SEPARATOR)
        header[headerKeyValue[KEY].trim()] = headerKeyValue[VALUE].trim()
    }

    fun get(key: String): String? = header[key]

    fun getOrDefault(key: String): String = header.getOrDefault(key, DEFAULT_STRING)

}