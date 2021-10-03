abstract class Validator<T> {
    abstract fun validate(value: T?): List<ErrorCode>
}

class NameValidator : Validator<String>() {
    override fun validate(value: String?): List<ErrorCode> {
        val str = value?.trim()

        if (str.isNullOrEmpty())
            return listOf(ErrorCode.EMPTY_LINE)
        if (!str.matches("[ЁёА-я]{1,16}$".toRegex()))
            return listOf(ErrorCode.INVALID_NAME_FORMAT)
        return listOf()
    }
}

class PhoneValidator : Validator<String>() {
    override fun validate(value: String?): List<ErrorCode> {
        val str = value?.trim()

        if (str.isNullOrEmpty())
            return listOf(ErrorCode.EMPTY_LINE)
        if (!str.matches("(7|8)([0-9]{10})$".toRegex()))
            return listOf(ErrorCode.INVALID_PHONE_FORMAT)
        return listOf()
    }
}

class EmailValidator : Validator<String>() {
    override fun validate(value: String?): List<ErrorCode> {
        val str = value?.trim()

        if (str.isNullOrEmpty())
            return listOf(ErrorCode.EMPTY_LINE)
        if (str.length > 32)
            return listOf(ErrorCode.INVALID_EMAIL_FORMAT)
        if (!str.matches("([A-Za-z]+@[A-Za-z]+\\.[A-Za-z]+)$".toRegex()))
            return listOf(ErrorCode.INVALID_EMAIL_FORMAT)
        return listOf()
    }
}

class SnilsValidator : Validator<String>() {
    override fun validate(value: String?): List<ErrorCode> {
        val str = value?.trim()

        if (str.isNullOrEmpty())
            return listOf(ErrorCode.EMPTY_LINE)
        if (!str.matches("[0-9]{11}$".toRegex()))
            return listOf(ErrorCode.INVALID_SNILS)

        val controlNumberActual = str.substring(9, 11).toInt()
        var controlNumberExpected = 0
        for (i in 1..9)
            controlNumberExpected += str[i - 1].digitToInt() * (10 - i)
        controlNumberExpected %= 101
        if (controlNumberExpected == 100) controlNumberExpected = 0
        if (controlNumberActual != controlNumberExpected)
            return listOf(ErrorCode.INVALID_SNILS)
        return listOf()
    }
}