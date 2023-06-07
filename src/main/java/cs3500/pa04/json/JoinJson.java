package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

public record JoinJson(@JsonProperty("join") String join,
                       @JsonProperty("arguments") JsonNode arguments) {
}