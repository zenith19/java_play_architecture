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
import validations.ProductValidation;
import java.util.List;

/**
 * Created by rownak on 10/25/16.
 */

@Security.Authenticated(NeedLogin.class)
public class ProductController extends Controller {
    private final ProductService productService;
    private final MessagesApi messagesApi;
    private final ProductValidation productValidation;
    private final FormFactory formFactory;

    // TODO:; AsyncResult only use slow process, Return Normal Result  in generray case. Because, Async and CompletableStage is difficult.

    // TODO : please use constructor injection.
    @Inject
    public ProductController(ProductService productService, MessagesApi messagesApi, FormFactory formFactory, ProductValidation productValidation) {
        this.productService = productService;
        this.messagesApi = messagesApi;
        this.formFactory = formFactory;
        this.productValidation = productValidation;
    }

    // TODO; return Result, not but ComletionStage<Rusult>;
    public Result create() {

        Form<Product> productForm = formFactory.form(Product.class).bindFromRequest();
//         OK, JSON validation is performed in controller.
        if (productForm.hasErrors()) {
            JsonNode jsonError = productForm.errorsAsJson();
            return ok(jsonError);
        }

        // TODO: but, service input and output should be DTO or Entity or Javabeans.
        Product product = productForm.get();


        JsonNode resultJson = null;
        try {
            productValidation.validation(product);
            Product ret = productService.create(product);
            resultJson = Json.toJson(ret);
        }
        //        catch (RuntimeException e) {
//            //TODO: please doesn't use Exception. use appropriate exception.
//            //TODO: And, if RuntimeException raises here, handle RuntimeExcetion in ErrorHandler, not controller.
//
//            resultJson = Json.toJson(e.getMessage());
//        }
        finally {
            return ok(resultJson);
        }
    }

    // TODO; return Result, not but ComletionStage<Rusult>;
    public Result getAll() {
        // TODO: please fix service intput/output from json to Java object.
        List<Product> products = null;
        try {
            products = productService.getAll();
        }
        finally {
            return ok(Json.toJson(products));
        }
    }

    // TODO; return Result, not but ComletionStage<Rusult>;
    public Result get(String productId) {
        JsonNode jsonNode = null;
        try {
            // TODO: please fix service intput/output from json to Java object.
            Product product = productService.get(productId);
            jsonNode = Json.toJson(product);
        }
        //catch (IllegalStateException e) {
//            jsonNode = Json.toJson(e.getMessage());
//        }
        finally {
            return ok(jsonNode);
        }
    }

    // TODO; return Result, not but ComletionStage<Rusult>;
    public Result delete(String productId) {

        //get lang from http req
        play.i18n.Lang lang = Http.Context.current().lang();
        JsonNode jsonNode = null;
        try {
            // TODO: please fix service input/output from json to Java object.
            productService.delete(productId);
            jsonNode = Json.toJson(messagesApi.get(lang, "deleteSuccess"));
        }
        finally {
            return ok(jsonNode);
        }
    }

    //@Inject
    //ActorSystem akka;

    // TODO; return Result, not but ComletionStage<Rusult>; custom thread pool is a just sample.
    public Result update(String productId) {
        Form<Product> productForm = formFactory.form(Product.class).bindFromRequest();
        if (productForm.hasErrors()) {
            JsonNode jsonError = productForm.errorsAsJson();
            return ok(jsonError);
        }

        Product product = productForm.get();
        JsonNode jsonNode = null;
        try {
            Product updatedProduct = productService.update(product, productId);
            jsonNode = Json.toJson(updatedProduct);
        }
        finally {
            return ok(jsonNode);
        }
    }
}
