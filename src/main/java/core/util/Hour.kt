package core.util

data class Hour(private val hour: Int) {

    fun getMessage(): String {
        if(hour < 12) {
            return "오전"
        }
        return "오후"
    }
}