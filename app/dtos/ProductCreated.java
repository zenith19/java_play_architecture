package dtos;

/**
 * Created by zenith on 11/11/16.
 */
public class ProductCreated {
    private String productId;
    private String productName;
    private Integer price;
    private String message;

    public String getProductId() {
        return productId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
