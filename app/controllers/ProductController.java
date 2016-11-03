package controllers;

import authorization.NeedLogin;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import models.Product;
import play.data.Form;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import services.ProductService;

/**
 * Created by rownak on 10/25/16.
 */

@Security.Authenticated(NeedLogin.class)
public class ProductController extends Controller {
    private final ProductService productService;
    private final MessagesApi messagesApi;
    // TODO : please use constructor injection.
    //@Inject
    private final FormFactory formFactory;

    // TODO:; AsyncResult only use slow process, Return Normal Result  in generray case. Because, Async and CompletableStage is difficult.
    // @Inject
    // HttpExecutionContext ec;

    @Inject
    public ProductController(ProductService productService, MessagesApi messagesApi, FormFactory formFactory) {
        this.productService = productService;
        this.messagesApi = messagesApi;
        this.formFactory = formFactory;
    }

    // async product creation using default threadpool
    // TODO; return Result, not but ComletionStage<Rusult>;
    public Result create() {

        Form<Product> productForm = formFactory.form(Product.class).bindFromRequest();
        // OK, JSON validation is performed in controller.
        if (productForm.hasErrors()) {
            JsonNode jsonError = productForm.errorsAsJson();
            return ok(jsonError);
        }
        // TODO: but, service input and output should be DTO or Entity or Javabeans.
        Product product = productForm.get();
        JsonNode resultJson = null;
        try {
            Product ret = productService.create(product);
            resultJson = Json.toJson(ret);
        } catch (Exception e) {
            //TODO: please doesn't use Exception. use appropriate exception.
            //TODO: And, if RuntimeException raises here, handle RuntimeExcetion in ErrorHandler, not controller.
            resultJson = Json.toJson(e.getMessage());
        }
        return ok(resultJson);
    }

    //async find all products using default threadpool
    // TODO; return Result, not but ComletionStage<Rusult>;
    public Result getAll() {
        // TODO: please fix service intput/output from json to Java object.
        JsonNode jsonNode = productService.getAll();
        return ok(jsonNode);
    }

    //async find product using default threadpool
    // TODO; return Result, not but ComletionStage<Rusult>;
    public Result get(String productId) {
        JsonNode jsonNode = null;
        try {
            // TODO: please fix service intput/output from json to Java object.
            jsonNode = productService.get(productId);
        } catch (Exception e) {
            jsonNode = Json.toJson(e.getMessage());
        }
        return ok(jsonNode);
    }

    // async product delete using default threadpool
    // TODO; return Result, not but ComletionStage<Rusult>;
    public Result delete(String productId) {

        //get lang from http req
        play.i18n.Lang lang = Http.Context.current().lang();

        JsonNode jsonNode = null;
        try {
            // TODO: please fix service input/output from json to Java object.
            productService.delete(productId);
            jsonNode = Json.toJson(messagesApi.get(lang, "deleteSuccess"));
        } catch (Exception e) {
            jsonNode = Json.toJson(e.getMessage());
        }
        return ok(jsonNode);
    }

    //@Inject
    //ActorSystem akka;

    // async product update using custom threadpool
    // TODO; return Result, not but ComletionStage<Rusult>; custom thread pool is a just sample.
    public Result update(String productId) {

        //configure my-context in conf/application.conf and here call that dispatcher
        //Executor myExecutor = akka.dispatchers().lookup("my-context");

        Form<Product> productForm = formFactory.form(Product.class).bindFromRequest();
        if (productForm.hasErrors()) {
            JsonNode jsonError = productForm.errorsAsJson();
            return ok(jsonError);
        }

        JsonNode jsonNode = null;
        try {
            JsonNode json = request().body().asJson();
            jsonNode = productService.update(json, productId);
        } catch (Exception e) {
            jsonNode = Json.toJson(e.getMessage());
        }
        return ok(jsonNode);
    }
}
