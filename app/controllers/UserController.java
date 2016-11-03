package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import models.User;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import services.UserService;

/**
 * Created by zenith on 10/25/16.
 */
@Singleton
public class UserController extends Controller{
    private final UserService userService;
    private final FormFactory formFactory;

    @Inject
    public UserController(UserService userService, FormFactory formFactory) {
        this.userService = userService;
        this.formFactory = formFactory;
    }

    public Result create(){
        Form<User> formUser = formFactory.form(User.class).bindFromRequest();
        if (formUser.hasErrors()) {
            JsonNode jsonError = formUser.errorsAsJson();
            return Results.badRequest(jsonError);
        }
        User user = null;
        try{
            user = userService.create(formUser.get());
        }catch (Exception e){
            Json.toJson(e.getMessage());
        }
        return ok(Json.toJson(user));
    }
}
