package calculator

class StringCalculator {
    fun add(text: String?): Int {
        if (text == null || text.isEmpty()) {
            return 0
        }

        val values = text.split(",")
        var sum = 0

        for(value in values) {
            sum += Integer.parseInt(value)
        }
        return sum
    }
}