package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Record for a fleet
 *
 * @param ships List of serializable ships
 */
public record FleetJson(@JsonProperty("fleet") List<ShipAdapter> ships) {

}
