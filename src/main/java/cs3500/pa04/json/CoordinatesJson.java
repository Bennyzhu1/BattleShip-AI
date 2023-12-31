package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.model.Coord;
import java.util.List;

/**
 * General serializable record for successful hits, report damage, take shots, and
 *
 * @param coordinates The list of successful hits
 */
public record CoordinatesJson(@JsonProperty("coordinates") List<Coord> coordinates) {
}
