import io.kotest.core.extensions.TestCaseExtension
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import org.junit.jupiter.api.Test

open class RepeatOnFailureExtension : TestCaseExtension {
    override suspend fun intercept(testCase: TestCase, execute: suspend (TestCase) -> TestResult): TestResult {
        val maxRerunsCount = 3
        var rerunsCounter = 0
        var testResult: TestResult
        var testPassed = false
        do {
            when (val executionResult = execute.invoke(testCase)) {
                is TestResult.Failure, is TestResult.Error -> {
                    testResult = executionResult
                    rerunsCounter++
                }
                is TestResult.Success, is TestResult.Ignored -> {
                    testResult = executionResult
                    testPassed = true
                }
            }

        } while (rerunsCounter < maxRerunsCount || testPassed == true)

        return testResult
    }
}


class TestingTest : RepeatOnFailureExtension() {
    @Test
    fun testingTest() {
        val a = 23
        val b = 2
        val c = a+b
        val result = 3
        assert(c == result) {"Ошибка, штука считает неправильно"}
        println("Штука считается правильно")
    }
}