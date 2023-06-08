package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.Direction;
import cs3500.pa04.model.Ship;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Serializable structure for ships
 */
public class ShipAdapter {
  private final Coord coord;
  private final int length;
  private final Direction direction;

  /**
   * Constructor for ShipAdapter by passing in a Ship
   *
   * @param ship The Ship
   */
  public ShipAdapter(Ship ship) {
    this(ship.coords()[0], ship.coords().length,
        Arrays.stream(ship.coords()).allMatch(coord -> coord.y() == ship.coords()[0].y())
            ? Direction.VERTICAL : Direction.HORIZONTAL);
  }

  /**
   * Constructor for ShipAdapter by passing in a ship's attributes
   *
   * @param coord     Starting coordinate
   * @param length    Length of ship (or size)
   * @param direction Direction of the ship
   */
  @JsonCreator
  public ShipAdapter(
      @JsonProperty("coord") Coord coord,
      @JsonProperty("length") int length,
      @JsonProperty("direction") Direction direction) {
    this.coord = coord;
    this.length = length;
    this.direction = direction;
  }

  /**
   * Gets the starting coordinate
   *
   * @return The starting coordinate
   */
  public Coord getCoord() {
    return this.coord;
  }

  /**
   * Gets the length of the ship
   *
   * @return The length of the ship
   */
  public int getLength() {
    return this.length;
  }

  /**
   * Gets the direction
   *
   * @return The direction of the ship
   */
  public Direction getDirection() {
    return this.direction;
  }
}
