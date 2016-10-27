package controllers;

import akka.actor.ActorSystem;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
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

    @Inject
    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Inject
    ActorSystem akka;

    public CompletionStage<Result> login() throws Exception{
        Executor myExecutor = akka.dispatchers().lookup("my-context");

        return CompletableFuture.supplyAsync(()->{
            JsonNode json = request().body().asJson();
            String authToken = null;
            try {
                authToken = sessionService.login(json);
            } catch (Exception e) {
                e.printStackTrace();
            }
            JsonNode jsonNode = Json.toJson(authToken);
            return ok(jsonNode);
        }, new HttpExecutionContext(myExecutor).current());
    }

    public CompletionStage<Result> logout() throws Exception{
        Executor myExecutor = akka.dispatchers().lookup("my-context");
        return CompletableFuture.supplyAsync(()->{
            String authToken = request().getHeader("Authorization");
            return sessionService.logout(authToken);
        }, new HttpExecutionContext(myExecutor).current());
    }
}
