package cs3500.pa04.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record Coord(int x, int y) {
  @JsonCreator
  public Coord(@JsonProperty("x") int x,
               @JsonProperty("y") int y) {
    this.x = x;
    this.y = y;
  }
}