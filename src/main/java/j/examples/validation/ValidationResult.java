package j.examples.validation;

import java.util.List;

public abstract sealed class ValidationResult permits ValidationResult.Success, ValidationResult.Failure {

    public abstract void check();

    public abstract boolean isSuccess();

    public abstract List<String> errorMessages();

    public static final class Success extends ValidationResult {
        private Success() {
        }

        @Override
        public void check() {

        }

        @Override
        public boolean isSuccess() {
            return true;
        }

        @Override
        public List<String> errorMessages() {
            return List.of();
        }

        public static final Success INSTANCE = new Success();
    }

    public abstract sealed static class Failure extends ValidationResult permits SingleFailure, CombinedFailure {

        @Override
        public void check() {
            throw new ValidationException(String.join(",", errorMessages()));
        }

        @Override
        public boolean isSuccess() {
            return false;
        }
    }

    public static final class SingleFailure extends Failure {
        private final String errorMessage;

        SingleFailure(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        @Override
        public List<String> errorMessages() {
            return List.of(errorMessage);
        }
    }

    public static final class CombinedFailure extends Failure {
        private final List<Failure> failures;

        CombinedFailure(Failure... failures) {
            this.failures = List.of(failures);
        }

        @Override
        public List<String> errorMessages() {
            return failures.stream()
                .flatMap(it -> it.errorMessages().stream())
                .toList();
        }
    }

    public static ValidationResult success() {
        return Success.INSTANCE;
    }

    public static ValidationResult failure(String message) {
        return new SingleFailure(message);
    }

    public static ValidationResult combine(ValidationResult... validationResults) {
        return combine(List.of(validationResults));
    }

    public static ValidationResult combine(List<ValidationResult> validationResults) {
        Failure[] failures = validationResults.stream()
            .filter(it -> it instanceof Failure).map(it -> (Failure) it)
            .toArray(Failure[]::new);

        if (failures.length > 0) {
            return new CombinedFailure(failures);
        } else {
            return Success.INSTANCE;
        }
    }
}


