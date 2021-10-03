import com.google.gson.Gson
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull

class ClientServiceTest {

    private val gson = Gson()
    private val clientService = ClientService()

    @Test
    fun `success save client`() {
        val client = getClientFromJson("/success/user.json")
        val result = clientService.saveClient(client)
        assertNotNull(result)
    }

    @Test
    fun `fail save client - validation errors`() {
        val client = getClientFromJson("/fail/user_data_corrupted.json")
        val exception = assertFailsWith<ValidationException>("Все поля ошибочны") {
            clientService.saveClient(client)
        }
        assertEquals(exception.errorCode[0], ErrorCode.EMPTY_LINE)
        assertEquals(exception.errorCode[1], ErrorCode.INVALID_NAME_FORMAT)
        assertEquals(exception.errorCode[2], ErrorCode.INVALID_PHONE_FORMAT)
        assertEquals(exception.errorCode[3], ErrorCode.INVALID_EMAIL_FORMAT)
        assertEquals(exception.errorCode[4], ErrorCode.INVALID_SNILS)
    }

    @Test
    fun `fail save client - first name validation error - null`() {
        val client = getClientFromJson("/fail/user_with_bad_first_name.json")
        val exception = assertFailsWith<ValidationException>("null в имени") {
            clientService.saveClient(client)
        }
        assertEquals(exception.errorCode[0], ErrorCode.EMPTY_LINE)
    }

    @Test
    fun `fail save client - last name validation error - empty line`() {
        val client = getClientFromJson("/fail/user_with_bad_last_name.json")
        val exception = assertFailsWith<ValidationException>("Пустая строка в фамилии") {
            clientService.saveClient(client)
        }
        assertEquals(exception.errorCode[0], ErrorCode.EMPTY_LINE)
    }

    @Test
    fun `fail save client - phone validation error - invalid format2`() {
        val client = getClientFromJson("/fail/user_with_bad_phone.json")
        val exception = assertFailsWith<ValidationException>("Недопустимая длина у тел.номера") {
            clientService.saveClient(client)
        }
        assertEquals(exception.errorCode[0], ErrorCode.INVALID_PHONE_FORMAT)
    }

    @Test
    fun `fail save client - phone validation error - invalid format`() {
        val client = getClientFromJson("/fail/user_with_bad_phone2.json")
        val exception = assertFailsWith<ValidationException>("Недопустимый формат у тел.номера") {
            clientService.saveClient(client)
        }
        assertEquals(exception.errorCode[0], ErrorCode.INVALID_PHONE_FORMAT)
    }

    @Test
    fun `fail save client - email validation error - invalid format`() {
        val client = getClientFromJson("fail/user_with_bad_email.json")
        val exception = assertFailsWith<ValidationException>("Недопустимый формат у почты") {
            clientService.saveClient(client)
        }
        assertEquals(exception.errorCode[0], ErrorCode.INVALID_EMAIL_FORMAT)
    }

    @Test
    fun `fail save client - snils validation error - invalid control number`() {
        val client = getClientFromJson("fail/user_with_bad_snils.json")
        val exception = assertFailsWith<ValidationException>("Недопустимое контрол.число у снилс") {
            clientService.saveClient(client)
        }
        assertEquals(exception.errorCode[0], ErrorCode.INVALID_SNILS)
    }

    private fun getClientFromJson(fileName: String): Client = this::class.java.getResource(fileName)
        .takeIf { it != null }
        ?.let { gson.fromJson(it.readText(), Client::class.java) }
        ?: throw Exception("Что-то пошло не так))")

}