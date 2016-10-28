package models;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import play.data.validation.Constraints;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by rownak on 10/25/16.
 */

@Entity
@Table(name = "products", schema = "java_play@cassandra_pu")
public class Product {
    @Id
    @Column(name = "product_id")
    private String productId;

    @Column(name = "product_name")
    @Length(min = 6, max = 50)
    @NotBlank
    @Constraints.Required(message = "Please enter Product name")
    private String productName;

    @Constraints.Required(message = "Please enter Price")
    @Range(min = 1, max = 9999999)
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
