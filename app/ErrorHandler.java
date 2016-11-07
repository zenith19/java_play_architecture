import exceptions.ApplicationException;
import play.Configuration;
import play.Environment;
import play.api.OptionalSourceMapper;
import play.api.routing.Router;
import play.http.DefaultHttpErrorHandler;
import play.i18n.MessagesApi;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by zenith on 10/25/16.
 */

@Singleton
public class ErrorHandler extends DefaultHttpErrorHandler {

    private final MessagesApi messagesApi;

    @Inject
    public ErrorHandler(Configuration configuration, Environment environment,
                        OptionalSourceMapper sourceMapper, Provider<Router> routes, MessagesApi messagesApi) {
        super(configuration, environment, sourceMapper, routes);
        this.messagesApi = messagesApi;
    }

    @Override
    protected CompletionStage<Result> onNotFound(Http.RequestHeader request, String message) {
        //TODO: delele console output.
        //System.out.println("call handler");

        // TODO : This app is backend server. so that, return 404 with json message.
        return CompletableFuture.completedFuture(Results.notFound(Json.toJson("NOT FOUND.")));
    }

    @Override
    public java.util.concurrent.CompletionStage<Result> onServerError(Http.RequestHeader request,
                                                                      java.lang.Throwable exception) {
        exception.printStackTrace();
        // TODO : message handling example.
        if (exception instanceof ApplicationException) {
            // common ApplicationException handling.
            play.i18n.Lang lang = Http.Context.current().lang();
            ApplicationException ae = (ApplicationException)exception;
            return CompletableFuture.completedFuture(Results.
                    internalServerError(Json.toJson(messagesApi.get(lang, ae.getMessageCode()))));
        } else {
            // unexpected error.
            // TODO:  NotFound is strange. This should be Server error.
            return CompletableFuture.completedFuture(Results.internalServerError(Json.toJson(exception.getMessage())));
        }

    }
}
