package models;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.Constraint;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by rownak on 10/25/16.
 */

@Entity
@Table(name = "products")
public class Product {
    @Id
    @Column(name = "product_id")
    private String productId;

    /*
     @NotBlank() TODO:  shuld only use @Constraints.XXXX familly. beacuse @Constraints.XXXX can use message property in conf/message file.
     TODO : message can use message property in message file. if message attribute exists in conf/message file, conf/message is used as message.
     @Constraints.Required(message = "input.productName") TODO: <- input.productName is in conf/message.
     TODO : Also, you can override default message in conf/message file.
     TODO:  e.g, @Constraints.MinLength default meesage is "error.minLength", so thad you can define "error.minLength" in conf/message for override.
    */

    @Column(name = "product_name")
    @Constraints.Required(message = "input.productName")
    @Constraints.MinLength(2)
    @Constraints.MaxLength(50)
    private String productName;

    //TODO: Note.
    @Constraints.Required(message = "input.price")
    @Constraints.Min(1)
    @Constraints.Max(9999999)
    private Integer price;

    /*
     TODO : I recommend to define validate method for custom validation.
     TODO : custom validation writing is OK. but regex check is able to @Constraints.Regex.
     TODO: ValidationError's message also can use conf/message key. input.specialCharacter is defined in conf/message.
    */

    public List<ValidationError> validate() {
        List<ValidationError> errors = new ArrayList<>();
        if (!productName.matches("[^&%$#@!~]*")) {
            errors.add(new ValidationError("productName", "input.specialCharacter"));
        }

        return errors;
    }

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
