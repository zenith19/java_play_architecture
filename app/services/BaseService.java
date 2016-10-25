package services;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Created by rownak on 10/25/16.
 */

public interface BaseService {
    public JsonNode create(JsonNode jsonNode);
}
