package calculator

import java.lang.RuntimeException
import java.util.regex.Matcher
import kotlin.streams.toList


class StringCalculator {
    fun add(text: String?): Int {
        if (isBlank(text)) {
            return 0
        }

        return sum(toInts(split(text)))
    }

    private fun split(text: String?): List<String> {
        val regex: Regex = "//(.)\n(.*)".toRegex()
        if(regex.matches(text!!)) {
            val groupValues : List<String> = regex.find(text)!!.groupValues
            val customDelegate: String = groupValues[1]
            return groupValues[2].split(customDelegate)
        }

        return text.split("[,:]".toRegex())
    }

    private fun isBlank(text: String?) = text == null || text.isEmpty()

    private fun toInts(values: List<String>): List<Int> =
            values.stream().map { value -> toPositive(value) }.toList()

    private fun toPositive(value: String?): Int {
        val intValue = value!!.toInt()
        if(intValue < 0) {
            throw RuntimeException()
        }

        return intValue
    }

    private fun sum(values: List<Int>): Int {
        var sum = 0

        for (value in values) {
            sum += value
        }
        return sum
    }
}