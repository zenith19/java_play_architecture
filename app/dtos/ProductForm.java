package dtos;

import play.data.validation.Constraints;

/**
 * Created by zenith on 11/11/16.
 */
public class ProductForm {

    private String productId;

    @Constraints.Required(message = "input.productName")
    @Constraints.MinLength(2)
    @Constraints.MaxLength(50)
    private String productName;

    @Constraints.Required(message = "input.price")
    @Constraints.Min(1)
    @Constraints.Max(9999999)
    private Integer price;

    public String getProductId() {
        return productId;
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
