package junit5;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.migrationsupport.rules.EnableRuleMigrationSupport;
import org.junit.rules.ExternalResource;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@EnableRuleMigrationSupport
class RuleTest {

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
    void test() {
        assertFalse(testRuleが動いた);
        assertTrue(externalResourceが動いた);
    }
}
