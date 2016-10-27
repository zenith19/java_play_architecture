package auth;

import com.google.inject.Inject;
import models.Session;
import play.i18n.MessagesApi;
import play.mvc.Http;
import play.mvc.Security;
import services.SessionService;

/**
 * Created by rownak on 10/26/16.
 */

public class NeedLogin extends Security.Authenticator {
    private SessionService sessionService;
    private final MessagesApi messagesApi;

    @Inject
    public NeedLogin(SessionService sessionService, MessagesApi messagesApi) {
        this.sessionService = sessionService;
        this.messagesApi = messagesApi;
    }

    @Override
    public String getUsername(Http.Context ctx) {
        String authToken = ctx.request().getHeader("Authorization");
        Session session = sessionService.authenticate(authToken);
        if (session != null) {
            return authToken;
        } else {
            return null;
        }
    }

    @Override
    public play.mvc.Result onUnauthorized(Http.Context ctx) {
        play.i18n.Lang lang = Http.Context.current().lang();

        return unauthorized(messagesApi.get(lang, "invalidUser"));
    }
}
