package k.examples.validation

sealed class ValidationResult {
    abstract val success: Boolean
    abstract val messages: List<String>
    abstract fun check()

    object Success : ValidationResult() {
        override val success: Boolean = true
        override fun check() = Unit
        override val messages: List<String> = listOf()
    }

    abstract class Failure : ValidationResult() {
        override val success: Boolean = false
        override fun check() {
            throw ValidationException(messages.joinToString(", "))
        }
    }

    data class SingleFailure(private val message: String) : Failure() {
        override val messages: List<String>
            get() {
                return listOf(message)
            }
    }

    data class CombinedFailure(val failures: List<Failure>) : Failure() {
        override val messages: List<String>
            get() {
                return failures.flatMap { it.messages }
            }
    }

    companion object {
        fun success() = Success
        fun failure(message: String) = SingleFailure(message)
        fun combine(vararg validationResults: ValidationResult)
            : ValidationResult {
            val failures = validationResults
                .filterIsInstance<Failure>()

            return if (failures.isNotEmpty()) {
                CombinedFailure(failures)
            } else {
                Success
            }
        }

        fun combine(validationResults: Collection<ValidationResult>)
            : ValidationResult {
            return combine(*validationResults.toTypedArray())
        }
    }
}