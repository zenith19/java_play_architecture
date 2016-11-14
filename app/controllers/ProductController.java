package controllers;

import anotations.HasRoles;
import authentication.NeedLogin;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import dtos.ProductCreated;
import dtos.ProductForm;
import dtos.ProductInput;
import dtos.ProductOutput;
import models.Product;
import org.modelmapper.ModelMapper;
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
     TODO: In controller, DTO  binds Form and validation, then make model from DTO and call service (service input is model),
           then, make output DTO from service method return value, and make response json from output dto. In this case,
           DTO has only validation definition, and model has only Entity definition.
    */
    private final MessagesApi messagesApi;
    private final ProductService productService;
    private final FormFactory formFactory;
    private final ModelMapper modelMapper;

    /*
     TODO:; AsyncResult only use slow process, Return Normal Result  in generally case. Because, Async and CompletableStage is difficult.
     TODO : please use constructor injection.
    */
    @Inject
    public ProductController(ProductService productService, MessagesApi messagesApi, FormFactory formFactory, ModelMapper modelMapper) {
        this.productService = productService;
        this.messagesApi = messagesApi;
        this.formFactory = formFactory;
        this.modelMapper = modelMapper;
    }

    // TODO; return Result, not but CompletionStage<Result>;
    public Result create() {
        play.i18n.Lang lang = Http.Context.current().lang();
        Form<ProductInput> productForm = formFactory.form(ProductInput.class).bindFromRequest();

        // OK, JSON validation is performed in controller.
        if (productForm.hasErrors()) {
            JsonNode jsonError = productForm.errorsAsJson();
            //TODO: In validation error, return response as badRequest.
            return badRequest(jsonError);
        }
        // TODO: but, service input and output should be DTO or Entity or Javabeans.
        Product product = modelMapper.map(productForm.get(), Product.class);
        Product createdProduct = productService.create(product);
        ProductOutput productOutput = modelMapper.map(createdProduct, ProductOutput.class);
        productOutput.setMessage(messagesApi.get(lang(), "input.created"));
        return ok(Json.toJson(productOutput));
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
    @HasRoles({"admin", "default"})
    public Result get(String productId) {
        // TODO: please fix service input/output from json to Java object.
        Product product = productService.get(productId);
        ProductOutput productOutput = modelMapper.map(product, ProductOutput.class);
        return ok(Json.toJson(productOutput));
    }

    // TODO; return Result, not but CompletionStage<Result>;
    @HasRoles
    public Result delete(String productId) {
        //get lang from http req
        play.i18n.Lang lang = Http.Context.current().lang();
        // TODO: please fix service input/output from json to Java object.
        productService.delete(productId);

        return ok(Json.toJson(messagesApi.get(lang, "deleteSuccess")));
    }

    // TODO; return Result, not but CompletionStage<Result>; custom thread pool is a just sample.
    public Result update(String productId) {
        play.i18n.Lang lang = Http.Context.current().lang();
        Form<ProductForm> productForm = formFactory.form(ProductForm.class).bindFromRequest();
        if (productForm.hasErrors()) {
            JsonNode jsonError = productForm.errorsAsJson();
            return ok(jsonError);
        }
        Product product = modelMapper.map(productForm.get(), Product.class);
        Product serviceResult = productService.update(product, productId);
        ProductCreated productCreated =  modelMapper.map(serviceResult, ProductCreated.class);
        productCreated.setMessage(messagesApi.get(lang, "updateSuccess"));

        return ok(Json.toJson(productCreated));
    }
}
