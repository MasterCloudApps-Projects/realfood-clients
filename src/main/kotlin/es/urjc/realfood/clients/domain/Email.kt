package es.urjc.realfood.clients.domain

import java.lang.IllegalArgumentException

class Email(private val value: String) {

    init {
        if(isNotValidEmail(value))
            throw IllegalArgumentException("Email '$value' is not a valid email")
    }

    private fun isNotValidEmail(value: String): Boolean = !EMAIL_PATTERN_REGEX.matches(value)
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Email

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return value
    }

    companion object {
        val EMAIL_PATTERN_REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})".toRegex()
    }

}