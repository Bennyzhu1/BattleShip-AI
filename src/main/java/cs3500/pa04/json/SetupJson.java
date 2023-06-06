package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import cs3500.pa04.model.Ship;
import java.util.List;

public record SetupJson(@JsonProperty("setup") List<Ship> ships,
                        @JsonProperty("arguments") JsonNode arguments) {
}
