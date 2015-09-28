package trial.entity;

import org.seasar.doma.Domain;

import java.util.Objects;

@Domain(valueType = String.class)
public class CompanyName {

    private final String value;

    public CompanyName(String value) {
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
