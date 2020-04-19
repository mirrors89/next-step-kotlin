package app.model

class Result private constructor(val isStatus: Boolean, val message: String = "") {

    companion object {
        fun ok(): Result {
            return Result(true)
        }

        fun fail(message: String): Result {
            return Result(false, message)
        }
    }


    override fun toString(): String {
        return "Result [status=$isStatus, message=$message]"
    }

}