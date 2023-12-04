package k.examples.validators.condition


class NameValidator {
    fun validate(name: String): Boolean {
        if (name.length < MINIMAL_LENGTH || name.length > MAXIMAL_LENGTH) {
            return false
        }

        if (Character.isWhitespace(name[0]) || Character.isWhitespace(name[name.length - 1])) {
            return false
        }

        return true;
    }

    companion object {
        private const val MINIMAL_LENGTH = 1
        private const val MAXIMAL_LENGTH = 64
    }
}
