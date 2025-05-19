package vn.com.pharmacity.config;

import com.miragesql.miragesql.bean.BeanDescFactory;
import com.miragesql.miragesql.bean.FieldPropertyExtractor;
import com.miragesql.miragesql.integration.spring.SpringConnectionProvider;
import com.miragesql.miragesql.naming.RailsLikeNameConverter;
import com.miragesql.miragesql.provider.ConnectionProvider;
import jp.xet.springframework.data.mirage.repository.config.EnableMirageRepositories;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mirage.repository.support.MiragePersistenceExceptionTranslator;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import vn.com.pharmacity.config.service.impl.SqlManagerServiceImpl;


@EnableMirageRepositories(basePackages = "vn.com.pharmacity.repository", sqlManagerRef = "sqlManagerServicePr")
@Configuration
public class MirageConfig {

    @Autowired
    @Qualifier("transactionManagerSql")
    private DataSourceTransactionManager transactionManager;

    @Bean
    public BeanDescFactory beanDescFactory() {
        BeanDescFactory beanDescFactory = new BeanDescFactory();
        beanDescFactory.setPropertyExtractor(new FieldPropertyExtractor());
        return beanDescFactory;
    }

    @Bean("sqlManagerServicePr")
    @Primary
    public SqlManagerServiceImpl sqlManagerService() {
        // bridge java.util.logging used by mirage
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        SqlManagerServiceImpl sqlManagerServiceImpl = new SqlManagerServiceImpl();
        sqlManagerServiceImpl.setConnectionProvider(connectionProvider());
        sqlManagerServiceImpl.setDialect(new SQLServerDialect());
        sqlManagerServiceImpl.setBeanDescFactory(beanDescFactory());
        sqlManagerServiceImpl.setNameConverter(new RailsLikeNameConverter());
        return sqlManagerServiceImpl;
    }

    @Bean
    public MiragePersistenceExceptionTranslator persistenceExceptionTranslator() {
        return new MiragePersistenceExceptionTranslator();
    }

    @Bean(name="connectionProvider")
    @Primary
    public ConnectionProvider connectionProvider(){
        SpringConnectionProvider springConnectionProvider = new SpringConnectionProvider();
        springConnectionProvider.setTransactionManager(transactionManager);
        return springConnectionProvider;
    }
}
