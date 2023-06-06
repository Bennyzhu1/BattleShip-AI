package cs3500.pa04;

import cs3500.pa04.controller.BattleSalvoController;
import cs3500.pa04.model.AiPlayer;
import cs3500.pa04.model.ManualPlayer;
import cs3500.pa04.model.Player;
import java.net.Socket;

/**
 * Responsible for the application's entry point and running the client
 */
public class Driver {
  /**
   * Entry point method
   *
   * @param args Command-line arguments
   */
  public static void main(String[] args) {
    Player manualPlayer = new ManualPlayer();
    Player aiPlayer = new AiPlayer();

    BattleSalvoController bsc = new BattleSalvoController(manualPlayer, aiPlayer);
    try {
      bsc.runGame();
    } catch (RuntimeException e) {
      throw new RuntimeException("An unexpected error occurred.");
    }

//    Controller controller;
//    Socket socket;
//    Player player;
//
//    controller = new ProxyController(socket, player);
//    controller.run();
  }

  private static void runClient() {

  }
}
