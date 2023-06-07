package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.model.ShipType;
import java.util.Map;

/**
 * Serializable set-up adapter record to get the game specs and fleet
 *
 * @param width  The width
 * @param height The height
 * @param ships  Map of ships and their counts
 */
public record SetupAdapter(@JsonProperty("width") int width,
                        @JsonProperty("height") int height,
                        @JsonProperty("fleet-spec") Map<ShipType, Integer> ships) {
}
