package k.examples.rules.combined

import k.examples.validation.ValidationResult

class NameRule {
    fun validate(name: String): ValidationResult {
        val validationResults = mutableListOf<ValidationResult>()
        if (name.length < MINIMAL_LENGTH || name.length > MAXIMAL_LENGTH) {
            validationResults.add(
                ValidationResult.failure(
                    """The name "${name}" must have a length >=${MINIMAL_LENGTH} and <=${MAXIMAL_LENGTH}. Was ${name.length}"""
                )
            )
        }
        if (name.length > 0 && (Character.isWhitespace(name[0]) || Character.isWhitespace(name[name.length - 1]))) {
            validationResults.add(ValidationResult.failure("""The name "${name}" must start and end with a non-whitespace-character"""))
        }
        return ValidationResult.combine(validationResults)
    }

    companion object {
        private const val MINIMAL_LENGTH = 1
        private const val MAXIMAL_LENGTH = 64
    }
}
