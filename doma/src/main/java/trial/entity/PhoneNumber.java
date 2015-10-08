package trial.entity;

import java.util.Objects;

/**
 * 電話番号。
 * 外部ドメインのサンプルですよ。
 */
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
