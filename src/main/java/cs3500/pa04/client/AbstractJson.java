package cs3500.pa04.client;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AbstractJson(@JsonProperty("method-name") String methodName,
                           @JsonProperty("arguments") Json json) {
}
