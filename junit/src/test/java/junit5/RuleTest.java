package junit5;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.migrationsupport.rules.EnableRuleMigrationSupport;
import org.junit.rules.ExternalResource;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

@EnableRuleMigrationSupport
class RuleTest {

    @Rule
    public TestRule testRule = new TestRule() {
        @Override
        public Statement apply(Statement base, Description description) {
            System.out.println("testRule");
            return new Statement() {
                @Override
                public void evaluate() throws Throwable {
                    base.evaluate();
                }
            };
        }
    };

    @Rule
    public ExternalResource externalResource = new ExternalResource() {
        @Override
        protected void before() throws Throwable {
            System.out.println("externalResource");
        }
    };

    @Test
    void test() {
    }
}
