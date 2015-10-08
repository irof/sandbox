package trial.entity;

import org.seasar.doma.ExternalDomain;
import org.seasar.doma.jdbc.domain.DomainConverter;

/**
 * 外部ドメインを利用する場合に基本型と外部ドメインのインスタンスを相互変換してくれる子です。
 */
@ExternalDomain
public class PhoneNumberConverter implements DomainConverter<PhoneNumber, String> {

    @Override
    public String fromDomainToValue(PhoneNumber phoneNumber) {
        return phoneNumber.getValue();
    }

    @Override
    public PhoneNumber fromValueToDomain(String value) {
        if (value == null) return null;
        return new PhoneNumber(value);
    }
}
