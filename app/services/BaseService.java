package services;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Created by rownak on 10/25/16.
 */
// TODO: I think BaseService is not necessary.
public interface BaseService {
    public JsonNode create(JsonNode jsonNode);
}
