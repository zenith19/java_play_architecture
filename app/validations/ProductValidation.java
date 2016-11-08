package validations;

import com.google.inject.Inject;
import models.Product;
import play.data.validation.ValidationError;
import play.i18n.MessagesApi;
import play.mvc.Http;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by rownak on 10/31/16.
 */
// TODO: please use validate method in Object. I don't recommend this custom validator's way.
public class ProductValidation {

    // private final MessagesApi messagesApi;

    @Inject
    public ProductValidation() {
       // this.messagesApi = messagesApi;
    }
    // TODO: following is coding advice.
    // TODO:method name shuod be verb.
    public List<ValidationError> validate(Product product) /*throws Exception*/ { // TODO : shuldn't thow java.lang.Exception.
        //TODO: should return ValidationError, because message convert is performed in Controller and form..
        // TODO I don't recommend this custom validator, But if you want to use this, bind error to form in Controller.
        // TODO: Following is binding error example.
        /*// in Controller
          Form<Product> productForm = formFactory.form(Product.class).bindFromRequest();
          // call custom validator if basic validation error is none.
          if(!productForm.hasErrors()) {
            List<ValidationError> errors = customValidator.validate(product);
                for(ValidationError error : errors) {
                 productForm.reject(error.key(), error.message());
                }
            }
            if (productForm.hasErrors()) { // return error response.
                JsonNode jsonError = productForm.errorsAsJson();
                return badRequest(jsonError);
            }
         */

        List<ValidationError> errors = new ArrayList<>();

        if (!StringSpecialCharacterValidation(product.getProductName())) {
           errors.add(new ValidationError("productName", "specialCharacter"));
        }
        return errors;


    }

    //TODO : this should be private
    private boolean StringSpecialCharacterValidation(String input) {
        String REGEX = "[^&%$#@!~]*";
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

}
