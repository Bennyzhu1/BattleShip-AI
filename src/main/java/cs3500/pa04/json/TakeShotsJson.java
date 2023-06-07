package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.model.Coord;
import java.util.List;

/**
 * Serializable takeShots record
 *
 * @param takenShots The shots taken
 */
public record TakeShotsJson(@JsonProperty("coordinates") List<Coord> takenShots) {
}

