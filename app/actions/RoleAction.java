package actions;

import anotations.Role;
import com.google.inject.Inject;
import exceptions.ApplicationException;
import models.Session;
import models.User;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import services.SessionService;
import services.UserService;

import java.util.concurrent.CompletionStage;

/**
 * Created by rownak on 11/10/16.
 */
public class RoleAction extends Action<Role> {
    private SessionService sessionService;
    private UserService userService;

    @Inject
    public RoleAction(SessionService sessionService, UserService userService) {
        this.sessionService = sessionService;
        this.userService = userService;
    }

    @Override
    public CompletionStage<Result> call(Http.Context ctx) {
        Session session = sessionService.authenticate(ctx.request().getHeader("Authorization"));
        if (session != null) {
            User user = userService.getUser(session.getEmail());
            if(user.getAdmin() == null) {
                throw new ApplicationException("not admin");
            }
        }

        return delegate.call(ctx);
    }
}
