package spi;

/**
 * @author irof
 */
public class HelloHoge implements HelloSPI {
    @Override
    public String hello() {
        return "HOGE";
    }
}
