package k.examples.validation

sealed class ValidationResult {
    abstract val success: Boolean
    abstract fun check()

    abstract val errorMessages: List<String>

    object Success : ValidationResult() {
        override val success: Boolean = true
        override fun check() = Unit
        override val errorMessages: List<String> = listOf()
    }

    abstract class Failure : ValidationResult() {
        override val success: Boolean = false
        override fun check() {
            throw ValidationException(errorMessages.joinToString(", "))
        }
    }

    data class SingleFailure(
        private val errorMessage: String
    ) : Failure() {
        override val errorMessages: List<String>
            get() {
                return listOf(errorMessage)
            }
    }

    data class CombinedFailure(
        val failures: List<Failure>,
    ) : Failure() {
        override val errorMessages: List<String>
            get() {
                return failures.flatMap { it.errorMessages }
            }
    }

    companion object {
        fun success() = Success
        fun failure(message: String) = SingleFailure(message)
        fun combine(vararg validationResults: ValidationResult): ValidationResult {
            val failures = validationResults.filterIsInstance<Failure>()

            return if (failures.isNotEmpty()) {
                CombinedFailure(failures)
            } else {
                Success
            }
        }

        fun combine(validationResults: Collection<ValidationResult>): ValidationResult = combine(*validationResults.toTypedArray())
    }
}