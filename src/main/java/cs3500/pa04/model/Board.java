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
   * Forms the allay ship, placing all the ships on the board
   *
   * @param ships the list of ships that have been generated.
   */
  public void formShip(List<Ship> ships) {
    formEmptyBoard();
    for (Ship s : ships) {
      for (Coord c : s.getCoords()) {
        ShipType st = s.getShipType();
        board[c.getX()][c.getY()] = st.abrev;
      }
    }
  }

  /**
   * Forms an empty board for the ally and the enemy as a baseline
   */
  private void formEmptyBoard() {
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[i].length; j++) {
        enemyBoard[i][j] = "-";
        board[i][j] = "-";
      }
    }
  }

}
