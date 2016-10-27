package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import models.User;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import services.UserService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

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

    @Inject
    FormFactory formFactory;

    public CompletionStage<Result> registration(){

        return CompletableFuture.supplyAsync(()->{
            Form<User> user = formFactory.form(User.class).bindFromRequest();
            if (user.hasErrors()) {
                JsonNode jsonError = user.errorsAsJson();
                return ok(jsonError);
            }
            JsonNode json = request().body().asJson();
            JsonNode jsonNode = null;
            try{
                jsonNode = userService.registration(json);
            }catch (Exception e){
                jsonNode = Json.toJson(e.getMessage());
            }
            return ok(jsonNode);
           }, ec.current()
        );
    }
}
