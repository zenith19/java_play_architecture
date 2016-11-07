package helpers;

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import play.db.jpa.JPAApi;


public class SupplyEMInterceptor implements MethodInterceptor {

    private Provider<NoTxJPA> provider;

     public SupplyEMInterceptor(Provider<NoTxJPA> provider) {
         this.provider = provider;
     }


    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        return provider.get().withDefaultEm(() -> {

            try {
                return invocation.proceed();
            } catch (Throwable e) {
               throw new RuntimeException(e);
            }
        });
    }
}
