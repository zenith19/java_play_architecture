import com.datastax.driver.core.Cluster;
import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;
import de.leanovate.play.cassandra.evolutions.CassandraEndpointConfig;
import helpers.NoTxJPA;
import helpers.SupplyEM;
import helpers.SupplyEMInterceptor;
import play.db.jpa.JPAApi;
import scala.Function0;
import scala.collection.Seq;
import scala.collection.immutable.IndexedSeq;
import scala.collection.immutable.Vector;
import scala.collection.mutable.ArrayBuffer;
import scala.runtime.BoxedUnit;
import services.*;

import java.time.Clock;
import java.util.Arrays;

/**
 * This class is a Guice module that tells Guice how to bind several
 * different types. This Guice module is created when the Play
 * application starts.
 *
 * Play will automatically use any class called `Module` that is in
 * the root package. You can create modules in other locations by
 * adding `play.modules.enabled` settings to the `application.conf`
 * configuration file.
 */
public class Module extends AbstractModule {

    @Override
    public void configure() {
        // Use the system clock as the default implementation of Clock
        bind(Clock.class).toInstance(Clock.systemDefaultZone());
        // Ask Guice to create an instance of ApplicationTimer when the
        // application starts.
        bind(ApplicationTimer.class).asEagerSingleton();
        // Set AtomicCounter as the implementation for Counter.
        bind(Counter.class).to(AtomicCounter.class);

        // setting EM interceptor
        SupplyEMInterceptor interceptor = new SupplyEMInterceptor(getProvider(NoTxJPA.class));
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(SupplyEM.class), interceptor);
        bindInterceptor(Matchers.annotatedWith(SupplyEM.class), Matchers.any(), interceptor);

        // password encrypter
        bind(PasswordEncrypter.class).to(BcryptPasswordEncrypter.class);

        bind(CassandraEndpointConfig.class).to(LocalEndpointsConfig.class);
    }

    /** TODO; coning for cassandra evolutions.
     *  see: https://github.com/leanovate/play-cassandra-evolutions
     *  This plugin is written in Scala, but works in Java play.
     * */
    public static class LocalEndpointsConfig implements CassandraEndpointConfig {
        @Override
        public Seq<String> databases() {

            return scala.collection.JavaConversions.asScalaBuffer(Arrays.asList("cassandra"));
        }

        @Override
        public Cluster clusterForDatabase(String db) {
            return Cluster.builder().addContactPoints("127.0.0.1").build();
        }

        @Override
        public void executeWithLock(String db, Function0<BoxedUnit> block) {
            block.apply();
        }
    }

}
