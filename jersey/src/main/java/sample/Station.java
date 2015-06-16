package sample;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author irof
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Station {
    private final String number;
    private final String name;

    /**
     * @deprecated JAXBContextとかが使う
     */
    @Deprecated
    public Station() {
        this(null, null);
    }

    public Station(String number, String name) {
        this.number = number;
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return number + ":" + name;
    }
}
