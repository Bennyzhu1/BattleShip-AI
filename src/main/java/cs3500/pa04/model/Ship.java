package cs3500.pa04.model;

import cs3500.pa04.client.Coord;
import cs3500.pa04.model.ShipType;

/**
 * Record that holds the ship and its location
 *
 * @param shipType Type of ship
 * @param coords   Ship's coordinates
 */
public record Ship(ShipType shipType, Coord[] coords) {
}
