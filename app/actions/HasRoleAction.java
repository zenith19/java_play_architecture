package actions;

import anotations.HasRoles;
import com.google.inject.Inject;
import exceptions.ApplicationException;
import models.Session;
import models.User;
import play.libs.Json;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;
import services.AuthorizationService;
import services.SessionService;
import services.UserService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by rownak on 11/10/16.
 */

public class HasRoleAction extends Action<HasRoles> {
    private AuthorizationService service;
    private UserService userService;

    @Inject
    public HasRoleAction(AuthorizationService service) {
        this.service = service;
    }

    @Override
    public CompletionStage<Result> call(Http.Context ctx) {

        //TODO : role check logic should be defined in service.
        //TODO: You should have role name as HasRoles Annotation value.
        // TODO ; Because, Role might increase or change in future.
        /*
        if (session != null) {
            User user = userService.getUser(session.getEmail());
            if(user.getRoles().contains("admin")) {
                throw new ApplicationException("not admin");
            }
        }
        */
        if (service.hasAuthorization(ctx.request().getHeader("Authorization"), configuration.value())) {
            // OK
            return delegate.call(ctx);
        } else {
            // Invalid token or not have role.
            return CompletableFuture.completedFuture(Results.forbidden(Json.parse("\"you doesn't have authorization\"")));
        }
    }
}
