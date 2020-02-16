package calculator

class StringCalculator {
    fun add(text: String?): Int {
        return if (text == null || text.isEmpty()) {
            0
        } else text.toInt()
    }
}