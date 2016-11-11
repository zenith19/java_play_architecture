package services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import daos.ProductDao;
import exceptions.ApplicationException;
import helpers.NoTxJPA;
import models.Product;
import play.i18n.MessagesApi;
import play.mvc.Http;

import java.util.List;
import java.util.UUID;

/**
 * Created by rownak on 10/25/16.
 */

@Singleton
public class ProductService {
    // TODO: injection filed is final more better.Because of avoid miss injection.
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

    /*
     TODO: JsonNode doesn't use Service. I think, conversion between JSON and Object(DTO, Entity, Java Beans) is
      controller's responsibility, If service input/output is JsonNode, JsonNode may not be Product's json format,
      I think Json validation is performed in Controller.
    */
    public Product create(Product product) { /*TODO : avoid throws Exception */
        product.setProductId(UUID.randomUUID().toString());

        return jpa.withDefaultEm(() -> productDao.create(product));
    }

    public Product update(Product formProductData, String productId) {
        play.i18n.Lang lang = Http.Context.current().lang();
        return jpa.withDefaultEm(() -> {
            Product product = productDao.getProductById(productId);
            if (product == null) {
                // TODO: Doesn't use Exception, You should make or use Appropriate Exception;
                throw new ApplicationException(messagesApi.get(lang, "productNotFound"));
            }
            product.setProductName(formProductData.getProductName().trim());
            product.setPrice(formProductData.getPrice());

            return productDao.update(product);
        });
    }

    public void delete(String productId) {
        play.i18n.Lang lang = Http.Context.current().lang();
        jpa.withDefaultEm(() -> {
            Product product = productDao.getProductById(productId);
            if (product == null) {
                throw new ApplicationException(messagesApi.get(lang, "productNotFound"));
            }
            productDao.delete(product);
        });
    }

    //TODO: JsonNode doesn't use in Service.
    public Product get(String productId) {
        play.i18n.Lang lang = Http.Context.current().lang();

        return jpa.withDefaultEm(() -> {
            Product product = productDao.getProductById(productId);
            if (product == null) {
                throw new ApplicationException(messagesApi.get(lang, "productNotFound"));
            }

            return product;
        });
    }

    //TODO: JsonNode doesn't use in Service.
    public List<Product> getAll() {

        return jpa.withDefaultEm(() -> {
            List<Product> products = productDao.getAllProduct();

            return products;
        });
    }
}
