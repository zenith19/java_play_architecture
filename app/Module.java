import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;
import helpers.SupplyEM;
import helpers.SupplyEMInterceptor;
import services.*;

import java.time.Clock;

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
        SupplyEMInterceptor interceptor = new SupplyEMInterceptor();
        requestInjection(interceptor);
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(SupplyEM.class), interceptor);
        bindInterceptor(Matchers.annotatedWith(SupplyEM.class), Matchers.any(), interceptor);

        // password encrypter
        bind(PasswordEncrypter.class).to(BcryptPasswordEncrypter.class);

    }

}
