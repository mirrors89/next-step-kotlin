package core.nmvc

import core.annotation.RequestMethod

data class HandleKey(private val url: String,
                     private val requestMethod: RequestMethod)