package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.model.GameResult;

/**
 * Record for ending the game
 *
 * @param result The result of the game
 * @param reason The reason for the game's outcome
 */
public record EndGameJson(@JsonProperty("result") GameResult result,
                          @JsonProperty("reason") String reason) {
}