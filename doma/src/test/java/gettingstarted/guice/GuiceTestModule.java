package gettingstarted.guice;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.dialect.Dialect;
import org.seasar.doma.jdbc.dialect.H2Dialect;
import org.seasar.doma.jdbc.tx.LocalTransactionDataSource;

public class GuiceTestModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Dialect.class).to(H2Dialect.class);
        bind(LocalTransactionDataSource.class).toInstance(
                new LocalTransactionDataSource("jdbc:h2:mem:tutorial;DB_CLOSE_DELAY=-1", "sa", null)
        );
        bind(Config.class).annotatedWith(Names.named("config")).to(GuiceAppConfig.class);
    }
}
