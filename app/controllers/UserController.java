package controllers;

import akka.actor.ActorSystem;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import services.UserService;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;

/**
 * Created by zenith on 10/25/16.
 */
public class UserController extends Controller{
    UserService userService = new UserService();

    @Inject
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Inject
    HttpExecutionContext ec;

    public CompletionStage<Result> registration(){

        return CompletableFuture.supplyAsync(()->{
                JsonNode json = request().body().asJson();
                JsonNode jsonNode = userService.registration(json);
                return ok(jsonNode);
            }, ec.current()
        );
    }

    @Inject
    ActorSystem akka;

    public CompletionStage<Result> login() throws IOException{
        Executor myExecutor = akka.dispatchers().lookup("my-context");

        return CompletableFuture.supplyAsync(()->{
            JsonNode json = request().body().asJson();
            String authToken = userService.login(json);
            JsonNode jsonNode = Json.toJson(authToken);
            return ok(jsonNode);
        }, new HttpExecutionContext(myExecutor).current());
    }

}
