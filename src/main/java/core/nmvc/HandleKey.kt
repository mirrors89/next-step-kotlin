package core.nmvc

import core.annotation.RequestMethod

class HandleKey(private val url: String, private val requestMethod: RequestMethod) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HandleKey

        if (url != other.url) return false
        if (requestMethod != other.requestMethod) return false

        return true
    }

    override fun hashCode(): Int {
        var result = url.hashCode()
        result = 31 * result + requestMethod.hashCode()
        return result
    }

    override fun toString(): String {
        return "HandleKey(url='$url', requestMethod=$requestMethod)"
    }
}