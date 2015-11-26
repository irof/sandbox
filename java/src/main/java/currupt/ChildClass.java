package currupt;

/**
 * @author irof
 */
public class ChildClass extends ParentClass {

    private final Object hoge;

    ChildClass(String hoge) {
        this.hoge = hoge;
    }

    @Override
    protected void parentMethod() {
        System.out.println(hoge);
    }
}
