package cs3500.pa04.model;

import java.util.List;

/**
 * Class for the boards in the game
 */
public class Board {
  /**
   * The players board with ships
   */
  public String[][] board;
  /**
   * The enemys board without ships present
   */
  public String[][] enemyBoard;

  /**
   * Board Constructor
   *
   * @param height Height of the given board.
   * @param width  Width of the given board
   */
  public Board(int height, int width) {
    this.board = new String[width][height];
    this.enemyBoard = new String[width][height];
  }
  /**
   * Sets the board's shots
   *
   * @param shots      The coordinates of the shots taken
   * @param impactType The type of impact, hit or miss, H / M
   */
  public void setShots(List<Coord> shots, Impact impactType) {
    if (impactType == Impact.HIT) {
      for (Coord coord : shots) {
        if (this.grid[coord.x()][coord.y()] != 'H') {
          this.grid[coord.x()][coord.y()] = impactType.name().charAt(0);
          this.shipLocations.remove(coord);
        }
      }
      this.standingShips = new HashSet<>(this.shipLocations.values()).stream().toList();
    }

    if (impactType == Impact.MISS) {
      for (Coord coord : shots) {
        if (this.grid[coord.x()][coord.y()] != 'H') {
          this.grid[coord.x()][coord.y()] = 'M';
        }
      }
    }
  }
}
