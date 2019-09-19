package socialnetwork;

import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.orient.commons.core.OrientTransactionManager;
import org.springframework.data.orient.object.OrientObjectDatabaseFactory;
import org.springframework.data.orient.object.OrientObjectTemplate;

@Configuration
public class ApplicationConfiguration {
        /* only needed for local testing.
    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory factory =
                new TomcatEmbeddedServletContainerFactory();
        return factory;
    }
    */

    @Bean
    public com.tinkerpop.blueprints.impls.orient.OrientGraphFactory factory() {
        OrientGraphFactory factory = new OrientGraphFactory("plocal:./databases/test").setupPool(1,10);
        factory.setUseLightweightEdges(false);
        return factory;
    }

    @Bean
    public OrientObjectDatabaseFactory ofactory() {
        OrientObjectDatabaseFactory factory =  new OrientObjectDatabaseFactory();
        factory.setUrl("plocal:./databases/test");
        return factory;
    }



    @Bean
    public OrientTransactionManager transactionManager() {
        return new OrientTransactionManager(ofactory());
    }

    @Bean
    public OrientObjectTemplate objectTemplate() {
        return new OrientObjectTemplate(ofactory());
    }
}
