package junit4.v4_12;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Stopwatch;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

/**
 * {@link Stopwatch}と<code>static</code>にできるようになった{@link Rule}を無理矢理使ってみる。
 *
 * @author irof
 */
public class PracStopwatch {

    @Rule
    @ClassRule
    public static TestWatcher watcher = new TestWatcher() {
        @Override
        protected void starting(Description description) {
            System.out.println("starting:" + description);
        }

        @Override
        protected void finished(Description description) {
            System.out.println("finished:" + description);
        }
    };

    @Rule
    public Stopwatch watch = new Stopwatch() {

        @Override
        protected void finished(long nanos, Description description) {
            System.out.printf("method %s finished: %d nanos. %n", description.getMethodName(), nanos);
        }
    };

    @Test
    public void test1() throws Exception {
    }

    @Test
    public void test2() throws Exception {
    }

    @Test
    public void test3() throws Exception {
    }
}
