package blog.constructor;

import java.util.Locale;

/**
 * @author irof
 */
public class Main {

    public static void main(String[] args) {
        new Child("xxx");
    }
}

class Hoge {
    final int str;

    Hoge() {
        method();
        //this.str = "HOGERA";
        this.str = 100;
    }

    void method() {
        System.out.println(str);
    }
}

class Parent {

    final String arg;

    Parent(String arg) {
        init();
        this.arg = arg;
    }

    void init() {
    }
}

class Child extends Parent {

    Child(String arg) {
        super(arg);
    }

    @Override
    void init() {
        System.out.println(arg.toUpperCase(Locale.ROOT));
    }
}
