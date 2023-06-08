package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.model.Coord;
import java.util.List;

/**
 * Successful hits record
 *
 * @param successfulHits The list of successful hits
 */
public record SuccessfulHitsJson(@JsonProperty("coordinates") List<Coord> successfulHits) {
}