import com.fasterxml.jackson.databind.JsonNode;
import play.*;
import play.api.OptionalSourceMapper;
import play.api.UsefulException;
import play.api.routing.Router;
import play.http.DefaultHttpErrorHandler;
import play.libs.Json;
import play.mvc.Http.*;
import play.mvc.*;

import javax.inject.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by zenith on 10/25/16.
 */

@Singleton
public class ErrorHandler extends DefaultHttpErrorHandler {

    @Inject
    public ErrorHandler(Configuration configuration, Environment environment,
                        OptionalSourceMapper sourceMapper, Provider<Router> routes) {
        super(configuration, environment, sourceMapper, routes);
    }

    @Override
    protected CompletionStage<Result> onNotFound(Http.RequestHeader request, String message) {
        //TODO: delele console output.
        //System.out.println("call handler");

        // TODO : This app is backend server. so that, return 404 with json message.
        return CompletableFuture.completedFuture(Results.notFound(Json.toJson("NOT FOUND.")));
    }
}
