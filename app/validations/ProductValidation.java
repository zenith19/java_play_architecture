package validations;

import com.google.inject.Inject;
import models.Product;
import play.i18n.MessagesApi;
import play.mvc.Http;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by rownak on 10/31/16.
 */
public class ProductValidation {
    private final MessagesApi messagesApi;

    @Inject
    public ProductValidation(MessagesApi messagesApi) {
        this.messagesApi = messagesApi;
    }

    public void validation(Product product) throws Exception {
        play.i18n.Lang lang = Http.Context.current().lang();

        if (!StringSpecialCharacterValidation(product.getProductName())) {
           throw new Exception(messagesApi.get(lang, "specialCharacter"));
        }
    }
    public Boolean StringSpecialCharacterValidation(String input) {
        String REGEX = "[^&%$#@!~]*";
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

}
