package spi;

/**
 * @author irof
 */
public class HelloFuga implements HelloSPI {
    @Override
    public String hello() {
        return "FUGA";
    }
}
