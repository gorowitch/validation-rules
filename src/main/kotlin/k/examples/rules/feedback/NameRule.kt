package k.examples.rules.feedback


class NameRule {
    fun validate(name: String): List<String> {
        val messages = mutableListOf<String>()
        if (name.length < MINIMAL_LENGTH || name.length > MAXIMAL_LENGTH) {
            messages.add("""The name "${name}" must have a length >=${MINIMAL_LENGTH} and <=${MAXIMAL_LENGTH}. Was ${name.length}""")
        }
        if (name.length > 0 && (Character.isWhitespace(name[0]) || Character.isWhitespace(name[name.length - 1]))) {
            messages.add("""The name "${name}" must start and end with a non-whitespace-character""")
        }
        return messages.toList()
    }

    companion object {
        private const val MINIMAL_LENGTH = 1
        private const val MAXIMAL_LENGTH = 64
    }
}
