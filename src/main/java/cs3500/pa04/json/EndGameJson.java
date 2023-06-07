package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import cs3500.pa04.model.GameResult;

public record EndGameJson(@JsonProperty("end-game") GameResult gameEnd,
                          @JsonProperty("arguments") JsonNode arguments) {
}