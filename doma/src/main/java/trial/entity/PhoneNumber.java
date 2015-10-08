package trial.entity;

import org.seasar.doma.Domain;

import java.util.Objects;

public class PhoneNumber {

    private final String value;

    public PhoneNumber(String value) {
        Objects.requireNonNull(value);
        if (value.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
