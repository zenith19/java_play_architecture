package services;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import daos.ProductDao;
import helpers.NoTxJPA;
import models.Product;
import play.db.jpa.JPAApi;
import play.i18n.MessagesApi;
import play.libs.Json;
import play.mvc.Http;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * Created by rownak on 10/25/16.
 */

@Singleton
public class ProductService {
    // TODO: injection filed is final more better.Beacuse of avoid miss injection.
    private final ProductDao productDao;
    private final MessagesApi messagesApi;

    /* TODO:
     You should use JPAApi even if  you doesn't use transaction,
     And, create EntityManger, and close em are called once in service method.
     For example, I write JPA helper Api in non-transaction as sample.
  */
    private final NoTxJPA jpa; // if use transaction, use JPAApi.

    @Inject
    public ProductService(ProductDao productDao, MessagesApi messagesApi,NoTxJPA jpa) {
        this.productDao = productDao;
        this.messagesApi = messagesApi;
        this.jpa = jpa;
    }

    /*TODO:
           JsonNode doesn't use Service.
           I think, conversion betwwen JSON and Object(DTO, Entity, Java Beans) is controller's responsibility,
           If service input/output is JsonNode, JsonNode may not be Product's json format, I think Json validation is performed in Controller.
    */
    public Product create(Product product) { /*TODO : avoid thorws Exeception */
        product.setProductId(UUID.randomUUID().toString());

        return jpa.withDefaultEm(() -> productDao.create(product));
    }

    public JsonNode update(JsonNode jsonNode, String productId) throws Exception {
        play.i18n.Lang lang = Http.Context.current().lang();
        return jpa.withDefaultEm(() -> {
            Product product = productDao.getProductById(productId);

            if (product == null) {
                // TODO: Doesn't use Exception, You should make or use Appropriate Exception;
                throw new IllegalStateException(messagesApi.get(lang, "productNotFound"));
            }
            Product formProduct = Json.fromJson(jsonNode, Product.class);

            product.setProductName(formProduct.getProductName());
            product.setPrice(formProduct.getPrice());

            return productDao.update(product);
        });
    }

    public void delete(String productId) throws Exception {
        play.i18n.Lang lang = Http.Context.current().lang();

        jpa.withDefaultEm(() -> {

            Product product = productDao.getProductById(productId);

            if (product == null) {
                throw new IllegalStateException(messagesApi.get(lang, "productNotFound"));
            }

            productDao.delete(product);
        });
    }
    //TODO: JsonNode doesn't use in Service.
    public JsonNode get(String productId) throws Exception {
        play.i18n.Lang lang = Http.Context.current().lang();

        return jpa.withDefaultEm(() -> {

            Product product = productDao.getProductById(productId);

            if (product == null) {
                throw new IllegalStateException(messagesApi.get(lang, "productNotFound"));
            }

            return Json.toJson(product);
        });
    }

    //TODO: JsonNode doensn't use in Service.
    public JsonNode getAll() {

        return jpa.withDefaultEm(() -> {
            List<Product> products = productDao.getAllProduct();
            return Json.toJson(products);
        });
    }
}
