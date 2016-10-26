package services;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Singleton;
import daos.ProductDao;
import models.Product;
import play.libs.Json;

import java.util.UUID;

/**
 * Created by rownak on 10/25/16.
 */

@Singleton
public class ProductService {
    ProductDao productDao = new ProductDao();

    public JsonNode create(JsonNode jsonNode) {
        Product product = Json.fromJson(jsonNode, Product.class);
        product.setProductId(UUID.randomUUID().toString());
        return productDao.create(product);
    }

    public JsonNode update(JsonNode jsonNode, String productId) {
        Product product = productDao.getProductById(productId);
        Product formProduct = Json.fromJson(jsonNode, Product.class);

        if (formProduct.getProductName().trim() != null) {
            product.setProductName(formProduct.getProductName());
        }

        if (formProduct.getPrice().toString().trim() != null) {
            product.setPrice(formProduct.getPrice());
        }

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
}
