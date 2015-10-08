package trial.entity;

import org.seasar.doma.ExternalDomain;
import org.seasar.doma.jdbc.domain.DomainConverter;

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
