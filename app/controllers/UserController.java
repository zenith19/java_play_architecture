package controllers;

import akka.actor.ActorSystem;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import com.google.inject.Singleton;
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
@Singleton
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
}
