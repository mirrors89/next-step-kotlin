package calculator

import kotlin.streams.toList


class StringCalculator {
    fun add(text: String?): Int {
        if (isBlank(text)) {
            return 0
        }

        return sum(toInts(split(text)))
    }

    private fun split(text: String?) = text!!.split("[,:]".toRegex())

    private fun isBlank(text: String?) = text == null || text.isEmpty()

    private fun toInts(values: List<String>): List<Int> =
            values.stream().map { value -> Integer.parseInt(value) }.toList()

    private fun sum(values: List<Int>): Int {
        var sum = 0

        for (value in values) {
            sum += value
        }
        return sum
    }
}