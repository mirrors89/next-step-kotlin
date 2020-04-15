package core.jdbc

import java.lang.RuntimeException

class DataAccessException : RuntimeException {
    constructor() : super()

    constructor(message: String?, cause: Throwable, enableSuppression: Boolean, writableStackTrace: Boolean)
            : super(message, cause, enableSuppression, writableStackTrace)

    constructor(message: String?, cause: Throwable) : super(message, cause)

    constructor(message: String?) : super(message)

    constructor(cause: Throwable) : super(cause)

}