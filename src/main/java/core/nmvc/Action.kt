package core.nmvc

import java.lang.Exception

interface Action {

    @Throws(Exception::class)
    fun execute(): String
}