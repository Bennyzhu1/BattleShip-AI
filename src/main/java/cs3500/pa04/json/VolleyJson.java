package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.model.Coord;
import java.util.List;

/**
 * Record for holding data on volleys
 *
 * @param shots The shots
 */
public record VolleyJson(@JsonProperty("volley") List<Coord> shots) {
}

