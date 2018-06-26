package junit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExternalResource;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import static org.junit.Assert.assertTrue;

public class RuleTest {

    boolean testRuleが動いた = false;

    @Rule
    public TestRule testRule = new TestRule() {
        @Override
        public Statement apply(Statement base, Description description) {
            testRuleが動いた = true;
            return new Statement() {
                @Override
                public void evaluate() throws Throwable {
                    base.evaluate();
                }
            };
        }
    };

    boolean externalResourceが動いた = false;

    @Rule
    public ExternalResource externalResource = new ExternalResource() {
        @Override
        protected void before() throws Throwable {
            externalResourceが動いた = true;
        }
    };


    @Test
    public void test() {
        assertTrue(testRuleが動いた);
        assertTrue(externalResourceが動いた);
    }
}
