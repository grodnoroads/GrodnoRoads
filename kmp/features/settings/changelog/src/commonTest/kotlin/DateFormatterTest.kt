import com.egoriku.grodnoroads.settings.changelog.domain.util.DateFormatter
import kotlin.test.Test
import kotlin.test.assertEquals

class DateFormatterTest {
    private val formatter = DateFormatter()

    @Test
    fun testFormatting() {
        val date = formatter.formatTime(1678395600)
        assertEquals("10 March, 2023", date)
    }
}