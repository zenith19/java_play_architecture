package controllers;

import anotations.HasRoles;
import authentication.NeedLogin;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import dtos.UserCreated;
import dtos.UserForm;
import models.User;
import org.modelmapper.ModelMapper;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import play.mvc.Security;
import services.UserService;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by zenith on 10/25/16.
 */
@Singleton
public class UserController extends Controller {
    private final UserService userService;
    private final FormFactory formFactory;
    private final ModelMapper mapper;

    @Inject
    public UserController(UserService userService, FormFactory formFactory, ModelMapper mapper) {
        this.userService = userService;
        this.formFactory = formFactory;
        this.mapper = mapper;

        createDefaultAdminUser();
    }

    // this method test purpose.
    private void createDefaultAdminUser(){

        User admin = new User();
        admin.setEmail("admin@admin");
        admin.setPassword("password");
        admin.setName("admin");
        admin.setBranch("b1");
        userService.create(admin, true);
        System.out.println("admin@admin/password is created.");

    }

    public Result create() {
        // TODO: Note. It depends on your development policy whether Controller's Input/Output is DTO only or allowing model.
        // TODO: bind form with DTO
        Form<UserForm> formUser = formFactory.form(UserForm.class).bindFromRequest();
        if (formUser.hasErrors()) {
            return Results.badRequest(formUser.errorsAsJson());
        }
        // TODO: then Form to Model by mapper.
        // TODO: mapper copy same name filed between difference class's object by default.
        User user = mapper.map(formUser.get(), User.class);
        User serviceResult = userService.create(user, false);
        UserCreated result =  mapper.map(serviceResult, UserCreated.class);
        //TODO : you can use custom attribute  when output is DTO.
        result.setMessage("User created.");
        return ok(Json.toJson(result));
    }

    @HasRoles("admin") // only admin user can execute this method.
    public Result createAdmin() {
        Form<UserForm> formUser = formFactory.form(UserForm.class).bindFromRequest();
        if (formUser.hasErrors()) {
            return Results.badRequest(formUser.errorsAsJson());
        }
        User user = mapper.map(formUser.get(), User.class);
        User serviceResult = userService.create(user,true);
        UserCreated result =  mapper.map(serviceResult, UserCreated.class);
        result.setMessage("Admin created.");
        return ok(Json.toJson(result));
    }



    @Security.Authenticated(NeedLogin.class)
    public Result update() {
        Form<User> userForm = formFactory.form(User.class).bindFromRequest();
        if (userForm.hasErrors()) {
            JsonNode jsonError = userForm.errorsAsJson();
            return ok(jsonError);
        }

        User user = userForm.get();

        // TODO : Doesn't use finally instead of catch. It causes writing invalid HTTP response.
        User updatedUser = userService.update(user);
        return ok(Json.toJson(updatedUser));
    }

    public Result getUserGroupByBranch() {
        List<User> users = userService.getUserGroupByBranch();
        return ok(Json.toJson(users));
    }
}
