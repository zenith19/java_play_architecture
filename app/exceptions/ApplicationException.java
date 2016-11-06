package exceptions;

/***
 * common application exception. this has messeage code that extract in ErrorHandler/
 */
public class ApplicationException extends RuntimeException {

    private String messageCode;

    public ApplicationException(String messageCode) {
        super();
        this.messageCode = messageCode;
    }

    public String getMessageCode() {
        return this.messageCode;
    }

}
