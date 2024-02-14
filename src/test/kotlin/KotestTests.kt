import io.kotest.core.spec.KotestTestScope
import io.kotest.core.spec.TestCaseExtensionFn
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

@KotestTestScope
class BehSpecTest : BehaviorSpec({
    given("Проверка сложенек") {
        val val1 = (1..100).random()
        val val2 = (1..100).random()

        `when`("Складываем числа") {
            val val3 = val1 + val2

            then("Проверяем результат") {
                val3 shouldBe val1 + val2 + 1
            }
        }
    }
})