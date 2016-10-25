package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.mvc.Result;
import services.BaseService;
import services.ProductService;

import static play.mvc.Controller.request;
import static play.mvc.Results.ok;

/**
 * Created by rownak on 10/25/16.
 */
public class ProductController {

    public Result create() {
        BaseService baseService = new ProductService();
        try {
            JsonNode json = request().body().asJson();
            JsonNode jsonNode = baseService.create(json);
            return ok(jsonNode);
        } catch (Exception e) {
            return ok(e.getMessage());
        }
    }

}
