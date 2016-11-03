package controllers;

import akka.actor.ActorSystem;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import models.User;
import play.i18n.MessagesApi;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;
import services.SessionService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;

/**
 * Created by zenith on 10/26/16.
 */
@Singleton
public class SessionController extends Controller {
    private SessionService sessionService;
    private final MessagesApi messagesApi;

    @Inject
    public SessionController(SessionService sessionService, MessagesApi messagesApi) {
        this.sessionService = sessionService;
        this.messagesApi = messagesApi;
    }

    public Result login() {
        play.i18n.Lang lang = Http.Context.current().lang();
        JsonNode json = request().body().asJson();
        User user = Json.fromJson(json, User.class);
        JsonNode authToken;
        authToken = sessionService.login(user);
        if (authToken == null) {
            return Results.badRequest(messagesApi.get(lang,"sessionInvalid"));
        }else {
            return ok(authToken);
        }
    }

    public Result logout() {
        play.i18n.Lang lang = Http.Context.current().lang();

        String authToken = request().getHeader("Authorization");
        JsonNode jsonNode;
        try {
            sessionService.logout(authToken);
            jsonNode = Json.toJson(messagesApi.get(lang, "logout"));
        } catch (Exception e) {
            jsonNode = Json.toJson(messagesApi.get(lang, "logoutError"));
        }
        return ok(jsonNode);
    }
}
