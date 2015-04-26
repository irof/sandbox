package sample.domain;

import javax.xml.bind.annotation.XmlElement;

public class Name {

    @XmlElement
    String firstName;

    @XmlElement
    String lastName;
}
