package k.examples.rules.guarding

import k.examples.validation.ValidationException

class NameRule {
    fun validate(name: String) {
        if (name.length < MINIMAL_LENGTH || name.length > MAXIMAL_LENGTH) {
            throw ValidationException(
                """The name "${name}" must have a length >=${MINIMAL_LENGTH} and <=${MAXIMAL_LENGTH}. Was ${name.length}"""
            )
        }
        if (Character.isWhitespace(name[0]) || Character.isWhitespace(name[name.length - 1])) {
            throw ValidationException("""The name "${name}" must start and end with a non-whitespace-character""")
        }
    }

    companion object {
        private const val MINIMAL_LENGTH = 1
        private const val MAXIMAL_LENGTH = 64
    }
}
