package cs3500.pa04.model;

import cs3500.pa04.view.BattleSalvoView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Abstract class for Player, used to represent Ai, Player, and possible more later on.
 */
public abstract class AbstractPlayer implements Player {
  /**
   * The board that consists of the enemy's empty and the personal board
   */
  protected Board allyBoard;
  /**
   * Sees if the game is over
   */
  protected boolean isGameOver;
  /**
   * A list of coords that have been used so another ship doesnt occupy the same tile
   */
  protected List<Coord> usedCoord = new ArrayList<>();
  /**
   * Temporary storage for coords to go in ships
   */
  protected List<Coord> tempCoords = new ArrayList<>();
  /**
   * Ships that are on the board
   */
  protected List<Ship> ships = new ArrayList<>();
  /**
   * Current number of tiles that have ships on them
   */
  protected int numOfTilesOccupied;
  /**
   * Number of ships present
   */
  protected int numOfShips;
  /**
   * Height of the ship
   */
  protected int shipHeight;
  /**
   * Width of the ship
   */
  protected int shipWidth;
  /**
   * Tiles that have a hit ship occupying it
   */
  protected List<Coord> tilesThatHitShips = new ArrayList<>();
  /**
   * Ships that are sunk and should not be able to "fire" at any enemy ships
   */
  protected List<Coord> alreadySunkShips = new ArrayList<>();
  /**
   * Common View field for the ships
   */
  protected BattleSalvoView view;

  /**
   * Constructor
   *
   * @param view used to standardize the view used throughout the code
   */
  AbstractPlayer(BattleSalvoView view) {
    this.view = view;
    this.isGameOver = false;
  }

  /**
   * Get the player's name.
   *
   * @return the player's name
   */
  @Override
  public abstract String name();

  /**
   * Given the specifications for a BattleSalvo board, return a list of ships with their locations
   * on the board.
   *
   * @param height         the height of the board, range: [6, 15] inclusive
   * @param width          the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences each ship should
   *                       appear on the board
   * @return the placements of each ship on the board
   */
  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    shipHeight = height;
    shipWidth = width;
    final ShipType carrier = ShipType.CARRIER;
    final ShipType battleship = ShipType.BATTLESHIP;
    final ShipType destroyer = ShipType.DESTROYER;
    final ShipType submarine = ShipType.SUBMARINE;
    generateShipCoords(carrier, specifications, height, width);
    generateShipCoords(battleship, specifications, height, width);
    generateShipCoords(destroyer, specifications, height, width);
    generateShipCoords(submarine, specifications, height, width);
    numOfTilesOccupied = specifications.get(carrier) * carrier.size
        + specifications.get(battleship) * battleship.size
        + specifications.get(destroyer) * destroyer.size
        + specifications.get(submarine) * submarine.size;
    numOfShips = specifications.get(carrier) + specifications.get(battleship)
        + specifications.get(destroyer) + specifications.get(submarine);
    allyBoard = new Board(height, width);
    allyBoard.formShip(ships);
    return ships;
  }

  /**
   * generates the coords for each ships
   *
   * @param shipType       the type of ship that needs to be generated
   * @param specifications how many of those types of ships need to be generated
   * @param height         the height of the board
   * @param width          the width of the board
   */
  private void generateShipCoords(ShipType shipType, Map<ShipType, Integer> specifications,
                                  int height, int width) {
    int xbound = width - shipType.size + 1;
    int ybound = height - shipType.size + 1;
    for (int i = 0; i < specifications.get(shipType); i++) {
      Random rand = new Random();
      int horv = rand.nextInt(0, 2);
      int x;
      int y;
      // Ship will be vertical
      if (horv == 0) {
        x = rand.nextInt(width);
        y = rand.nextInt(ybound);
        if (coordsAreValid(x, y, shipType, "vertical")) {
          list(shipType);
        } else {
          tempCoords.clear();
          i--;
        }
      } else {
        x = rand.nextInt(xbound);
        y = rand.nextInt(height);
        if (coordsAreValid(x, y, shipType, "horizontal")) {
          list(shipType);
        } else {
          tempCoords.clear();
          i--;
        }
      }
    }
  }

  /**
   * used to add coords to the list of ships
   *
   * @param shipType specifty the ship that will be added to the ship list
   */
  private void list(ShipType shipType) {
    Coord[] coordsToAdd = new Coord[this.tempCoords.size()];
    for (int i = 0; i < this.tempCoords.size(); i++) {
      Coord c = this.tempCoords.get(i);
      coordsToAdd[i] = new Coord(c.x(), c.y());
      usedCoord.add(c);
    }
    ships.add(new Ship(shipType, coordsToAdd));
    tempCoords.clear();
  }

  /**
   * Checks to see if the coords are valid
   *
   * @param x           X-value given
   * @param y           y-value iven
   * @param shipType    the ship type
   * @param orientation whether it is facing horizontally or vertically.
   * @return boolean value seeing if this ship is valid or not
   */
  private boolean coordsAreValid(int x, int y, ShipType shipType, String orientation) {
    boolean valid = true;
    int axisToChange;
    Coord coordToAdd;
    if (orientation.equals("vertical")) {
      axisToChange = y;
    } else {
      axisToChange = x;
    }
    for (int i = 0; i < shipType.size; i++) {
      if (orientation.equals("vertical")) {
        coordToAdd = new Coord(x, axisToChange);
      } else {
        coordToAdd = new Coord(axisToChange, y);
      }
      tempCoords.add(coordToAdd);
      if (usedCoord.contains(coordToAdd)) {
        valid = false;
      }
      axisToChange++;
    }
    return valid;
  }

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public abstract List<Coord> takeShots();

  /**
   * Given the list of shots the opponent has fired on this player's board, report which
   * shots hit a ship on this player's board.
   *
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   * @return a filtered list of the given shots that contain all locations of shots that hit a
   *         ship on this board
   */
  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    List<Coord> coordsThatHit = new ArrayList<>();
    for (Coord c : opponentShotsOnBoard) {
      if (usedCoord.contains(c)) {
        coordsThatHit.add(c);
        tilesThatHitShips.add(c);
      }
    }
    showHitsOnBoard(coordsThatHit, allyBoard.board);
    anyShipsSunk();
    return coordsThatHit;
  }

  /**
   * Checks to see if a ship has been sunk by checking if a ship has gotten all of its coords hit
   */
  private void anyShipsSunk() {
    for (Ship s : ships) {
      boolean shipSunk = true;
      for (Coord c : s.coords()) {
        if (!tilesThatHitShips.contains(c) || alreadySunkShips.contains(c)) {
          shipSunk = false;
          break;
        }
      }
      if (shipSunk) {
        numOfShips--;
        alreadySunkShips.addAll(Arrays.stream(s.coords()).toList());
      }
    }
  }

  /**
   * Reports to this player what shots in their previous volley returned from takeShots()
   * successfully hit an opponent's ship.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships.
   */
  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    showHitsOnBoard(shotsThatHitOpponentShips, allyBoard.enemyBoard);
  }

  /**
   * Shows the coords on the board
   *
   * @param coordsThatHit List of coords that have hit ships
   * @param side          marks the ally or enemy board depending on specifications
   */
  private void showHitsOnBoard(List<Coord> coordsThatHit, String[][] side) {
    for (Coord c : coordsThatHit) {
      side[c.x()][c.y()] = "H";
    }
  }

  /**
   * Notifies the player that the game is over.
   * Win, lose, and draw should all be supported
   *
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  @Override
  public void endGame(GameResult result, String reason) {
    //view.printToConsole("You " + result.outcome + " : " + reason);
  }
}
