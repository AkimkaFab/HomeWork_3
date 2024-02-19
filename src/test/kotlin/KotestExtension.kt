import io.kotest.core.extensions.TestCaseExtension
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult



object RepeatOnFailureExtension : TestCaseExtension {
    private var maxAttempt = 5

    override suspend fun intercept(testCase: TestCase, execute: suspend (TestCase) -> TestResult): TestResult {
        var result = execute(testCase)
        while (maxAttempt !=1 && result.isErrorOrFailure) {
            result = execute(testCase)
            maxAttempt--
        }
        return result
    }
}