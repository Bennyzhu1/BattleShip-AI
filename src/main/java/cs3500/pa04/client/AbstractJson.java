package cs3500.pa04.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

public record AbstractJson(@JsonProperty("method-name") String methodName,
                           @JsonProperty("arguments") JsonNode json) {
}
