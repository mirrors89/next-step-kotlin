package util

class HttpRequestUtils {

    companion object {

        /**
         * @param queryString은
         * URL에서 ? 이후에 전달되는 field1=value1&field2=value2 형식임
         * @return
         */
        fun parseQueryString(queryString: String): Map<String, String> {
            return parseValues(queryString, "&")
        }

        /**
         * @param 쿠키
         * 값은 name1=value1; name2=value2 형식임
         * @return
         */
        fun parseCookies(cookies: String): Map<String, String> {
            return parseValues(cookies, ";")
        }

        private fun parseValues(values: String, separator: String): Map<String, String> {
            if (values == null || values.isBlank()) {
                return hashMapOf()
            }
            return values.split(separator).map { t: String -> getKeyValue(t, "=") }
                    .filterNotNull()
                    .associateBy({ it.key }, { it.value })
        }

        fun getKeyValue(keyValue: String, regex: String?): Pair? {
            if (keyValue.isBlank()) {
                return null
            }
            val tokens = keyValue.split(regex!!).toTypedArray()
            return if (tokens.size != 2) {
                null
            } else Pair(tokens[0], tokens[1])
        }

        fun parseHeader(header: String): Pair? {
            return getKeyValue(header, ": ")
        }

    }

    class Pair internal constructor(key: String, value: String) {
        var key: String = key.trim { it <= ' ' }
        var value: String = value.trim { it <= ' ' }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Pair

            if (key != other.key) return false
            if (value != other.value) return false

            return true
        }

        override fun hashCode(): Int {
            var result = key.hashCode()
            result = 31 * result + value.hashCode()
            return result
        }

        override fun toString(): String {
            return "Pair [key=$key, value=$value]"
        }
    }
}