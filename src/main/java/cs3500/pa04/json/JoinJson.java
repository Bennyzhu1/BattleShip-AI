package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.model.GameType;

/**
 * Record for join response
 *
 * @param name Player name
 * @param gameType Type of game
 */
public record JoinJson(@JsonProperty("name") String name,
                       @JsonProperty("game-type") GameType gameType) {
}