package helpers;

import com.google.inject.Inject;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import play.db.jpa.JPAApi;


public class SupplyEMInterceptor implements MethodInterceptor {
    @Inject private NoTxJPA jpa;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        return jpa.withDefaultEm(() -> {

            try {
                return invocation.proceed();
            } catch (Throwable e) {
               throw new RuntimeException(e);
            }
        });
    }
}
