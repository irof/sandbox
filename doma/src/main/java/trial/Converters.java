package trial;

import org.seasar.doma.DomainConverters;
import trial.entity.PhoneNumberConverter;

/**
 * 外部ドメインを利用する場合にAnnotationProcessorにコンバーターを伝える子です。
 * AnnotationProcessorのOptionにFQCNを指定しておけばいい感じです。
 * <pre>
 *     doma.domain.converters=trial.Converters
 * </pre>
 */
@DomainConverters({PhoneNumberConverter.class})
public class Converters {
}
