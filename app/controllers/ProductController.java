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
    /*
     TODO: I recommend to define validate method in Form object, because this is play's standard and less code in controller.
     TODO: If you don't want to define validate method in model class, You can make DTO(Data Transfer Object).
     TODO: DTO is input/output object in controller, and this define validation annotations and validate method.
    */
    /*
     TODO: In controller, DTO  binds Form and validation, then make model from DTO and call service (service input is model),
           then, make output DTO from service method return value, and make response json from output dto. In this case,
           DTO has only validation definition, and model has only Entity definition.
    */
    private final MessagesApi messagesApi;
    private final ProductService productService;
    private final FormFactory formFactory;

    /*
     TODO:; AsyncResult only use slow process, Return Normal Result  in generally case. Because, Async and CompletableStage is difficult.
     TODO : please use constructor injection.
    */
    @Inject
    public ProductController(ProductService productService, MessagesApi messagesApi, FormFactory formFactory) {
        this.productService = productService;
        this.messagesApi = messagesApi;
        this.formFactory = formFactory;
    }

    // TODO; return Result, not but CompletionStage<Result>;
    public Result create() {
        Form<Product> productForm = formFactory.form(Product.class).bindFromRequest();

        // OK, JSON validation is performed in controller.
        if (productForm.hasErrors()) {
            JsonNode jsonError = productForm.errorsAsJson();
            //TODO: In validation error, return response as badRequest.

            return badRequest(jsonError);
        }
        // TODO: but, service input and output should be DTO or Entity or Javabeans.
        Product product = productForm.get();

        return ok(Json.toJson(productService.create(product)));
        /*
         TODO: please doesn't use Exception. use appropriate exception.
         TODO: And, if RuntimeException raises here, handle RuntimeException in ErrorHandler, not controller.
        */
    }

    // TODO; return Result, not but CompletionStage<Result>;
    public Result getAll() {
        // TODO: please fix service input/output from json to Java object.
        // TODO: doesn't use finally instead of catch. It causes invalid http response.

        return ok(Json.toJson(productService.getAll()));
    }

    // TODO; return Result, not but CompletionStage<Result>;
    public Result get(String productId) {
        // TODO: please fix service input/output from json to Java object.

        return ok(Json.toJson(productService.get(productId)));
    }

    // TODO; return Result, not but CompletionStage<Result>;
    public Result delete(String productId) {
        //get lang from http req
        play.i18n.Lang lang = Http.Context.current().lang();
        // TODO: please fix service input/output from json to Java object.
        productService.delete(productId);

        return ok(messagesApi.get(lang, "deleteSuccess"));
    }

    // TODO; return Result, not but CompletionStage<Result>; custom thread pool is a just sample.
    public Result update(String productId) {
        Form<Product> productForm = formFactory.form(Product.class).bindFromRequest();
        if (productForm.hasErrors()) {
            JsonNode jsonError = productForm.errorsAsJson();

            return ok(jsonError);
        }
        Product product = productForm.get();

        return ok(Json.toJson(productService.update(product, productId)));
    }
}
