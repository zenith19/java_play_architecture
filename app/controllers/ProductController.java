package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import services.ProductService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by rownak on 10/25/16.
 */
public class ProductController extends Controller {
    private ProductService productService;

    @Inject
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Inject
    HttpExecutionContext ec;

    // async product creation using default threadpool
    public CompletionStage<Result> create() {
        return CompletableFuture.supplyAsync(() -> {
            JsonNode json = request().body().asJson();
            JsonNode jsonNode = productService.create(json);
            return ok(jsonNode);
        }, ec.current());
    }

}
