package controllers;

import akka.actor.ActorSystem;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import com.google.inject.Singleton;
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

    @Inject
    ActorSystem akka;

    public CompletionStage<Result> login() {
        Executor myExecutor = akka.dispatchers().lookup("my-context");
        play.i18n.Lang lang = Http.Context.current().lang();

        return CompletableFuture.supplyAsync(()->{
            JsonNode json = request().body().asJson();
            JsonNode authToken = null;
            authToken = sessionService.login(json);
            if (authToken == null) {
                return Results.badRequest(messagesApi.get(lang,"sessionInvalid"));
            }else {
                return ok(authToken);
            }
        }, new HttpExecutionContext(myExecutor).current());
    }

    public CompletionStage<Result> logout() {
        Executor myExecutor = akka.dispatchers().lookup("my-context");
        play.i18n.Lang lang = Http.Context.current().lang();

        return CompletableFuture.supplyAsync(()->{
            String authToken = request().getHeader("Authorization");
            JsonNode jsonNode = null;
            try {
                sessionService.logout(authToken);
                jsonNode = Json.toJson(messagesApi.get(lang, "logout"));
            } catch (Exception e) {
                jsonNode = Json.toJson(messagesApi.get(lang, "logoutError"));
            }
            return ok(jsonNode);
        }, new HttpExecutionContext(myExecutor).current());
    }
}
