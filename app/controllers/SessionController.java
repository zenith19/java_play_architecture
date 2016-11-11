package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import dtos.SessionCreated;
import models.Session;
import models.User;
import org.modelmapper.ModelMapper;
import play.i18n.MessagesApi;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;
import services.SessionService;

/**
 * Created by zenith on 10/26/16.
 */
@Singleton
public class SessionController extends Controller {
    private SessionService sessionService;
    private final MessagesApi messagesApi;
    private final ModelMapper mapper;

    @Inject
    public SessionController(SessionService sessionService, MessagesApi messagesApi, ModelMapper mapper) {
        this.sessionService = sessionService;
        this.messagesApi = messagesApi;
        this.mapper = mapper;
    }

    public Result login() {
        play.i18n.Lang lang = Http.Context.current().lang();
        JsonNode json = request().body().asJson();
        User user = Json.fromJson(json, User.class);
        String authToken = sessionService.login(user).getAuthToken();
        if (authToken == null) {
            return Results.badRequest(messagesApi.get(lang,"sessionInvalid"));
        }else {
            Session serviceResult = sessionService.login(user);
            SessionCreated sessionCreated = mapper.map(serviceResult, SessionCreated.class);
            sessionCreated.setMessage(messagesApi.get(lang, "successfulLogin"));

            return ok(Json.toJson(sessionCreated));
        }
    }

    public Result logout() throws Exception{
        play.i18n.Lang lang = Http.Context.current().lang();
        String authToken = request().getHeader("Authorization");
        sessionService.logout(authToken);

        return ok(Json.toJson(messagesApi.get(lang, "logout")));
    }
}
