package cs3500.pa04.json;


import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.model.Coord;
import java.util.List;

/**
 * Report damage record
 *
 * @param shots List of coords that hit
 */
public record ReportDamageJson(@JsonProperty("coordinates") List<Coord> shots) {
}