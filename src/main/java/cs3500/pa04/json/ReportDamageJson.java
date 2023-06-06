package cs3500.pa04.json;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import cs3500.pa04.model.Coord;
import java.util.List;

public record ReportDamageJson(@JsonProperty("report-damage") List<Coord> shots,
                               @JsonProperty("arguments") JsonNode arguments) {
}