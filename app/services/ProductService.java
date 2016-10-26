package services;

import com.fasterxml.jackson.databind.JsonNode;
import daos.ProductDao;
import models.Product;
import play.libs.Json;

import java.util.UUID;

/**
 * Created by rownak on 10/25/16.
 */

public class ProductService {
    ProductDao productDao = new ProductDao();

    public JsonNode create(JsonNode jsonNode) {
        Product product = Json.fromJson(jsonNode, Product.class);
        product.setProductId(UUID.randomUUID().toString());
        return productDao.create(product);
    }
}
