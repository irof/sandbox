package junit4.v4_12;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Parameterized.UseParametersRunnerFactory;
import org.junit.runners.model.InitializationError;
import org.junit.runners.parameterized.BlockJUnit4ClassRunnerWithParameters;
import org.junit.runners.parameterized.ParametersRunnerFactory;
import org.junit.runners.parameterized.TestWithParameters;

import java.util.Arrays;
import java.util.List;

/**
 * {@link UseParametersRunnerFactory} を無理矢理使ってみる。
 *
 * @author irof
 */
@RunWith(Parameterized.class)
@UseParametersRunnerFactory(PracUseParametersRunnerFactory.FriendlyRunner.Factory.class)
public class PracUseParametersRunnerFactory {

    /**
     * パラメータが一つの時は単純なリストの <code>@parameters</code> と
     * 単一の <code>@Parameter</code> フィールドで良くなった。
     */
    @Parameters
    public static List<String> data() {
        return Arrays.asList("A", "B", "C");
    }

    @Parameter
    public String arg;

    @Test
    public void test() throws Exception {
    }

    /**
     * テストメソッド名がにこやかになる謎のRunner
     */
    public static class FriendlyRunner extends BlockJUnit4ClassRunnerWithParameters {

        public FriendlyRunner(TestWithParameters test) throws InitializationError {
            super(test);
        }

        @Override
        protected String getName() {
            return " (^-^) " + super.getName();
        }

        public static class Factory implements ParametersRunnerFactory {

            @Override
            public Runner createRunnerForTestWithParameters(TestWithParameters test) throws InitializationError {
                return new FriendlyRunner(test);
            }
        }
    }
}
