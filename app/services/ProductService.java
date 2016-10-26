package services;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import daos.ProductDao;
import models.Product;
import play.i18n.MessagesApi;
import play.libs.Json;
import play.mvc.Http;
import utils.Validation;

import java.util.List;
import java.util.UUID;

/**
 * Created by rownak on 10/25/16.
 */

@Singleton
public class ProductService {
    private ProductDao productDao;
    Validation validation = new Validation();
    private final MessagesApi messagesApi;

    @Inject
    public ProductService(ProductDao productDao, MessagesApi messagesApi) {
        this.productDao = productDao;
        this.messagesApi = messagesApi;
    }

    public JsonNode create(JsonNode jsonNode) throws Exception {
        Product product = Json.fromJson(jsonNode, Product.class);
        product.setProductId(UUID.randomUUID().toString());
        inputValidation(product);

        return productDao.create(product);
    }

    public JsonNode update(JsonNode jsonNode, String productId) throws Exception {
        Product product = productDao.getProductById(productId);
        Product formProduct = Json.fromJson(jsonNode, Product.class);

        product.setProductName(formProduct.getProductName());
        product.setPrice(formProduct.getPrice());
        inputValidation(product);

        return productDao.update(product);
    }

    public void delete(String productId) {
        Product product = productDao.getProductById(productId);
        productDao.delete(product);
    }

    public JsonNode get(String productId) {
        Product product = productDao.getProductById(productId);
        return Json.toJson(product);
    }

    public JsonNode getAll() {
        List<Product> products = productDao.getAllProduct();
        return Json.toJson(products);
    }

    private void inputValidation(Product product) throws Exception {
        play.i18n.Lang lang = Http.Context.current().lang();

        if (validation.validateString(product.getProductName()) == null) {
            throw new Exception(messagesApi.get(lang, "emptyProductName"));
        }

        if (product.getPrice() < 1 || product.getPrice() > 9999999) {
            throw new Exception(messagesApi.get(lang, "invalidProductPrice"));
        }
    }
}
