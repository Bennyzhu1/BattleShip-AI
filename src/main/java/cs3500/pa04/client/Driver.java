package cs3500.pa04.client;

import cs3500.pa04.controller.BattleSalvoController;
import cs3500.pa04.model.AiPlayer;
import cs3500.pa04.model.ManualPlayer;
import cs3500.pa04.model.Player;
import java.io.IOException;
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
    if (args.length == 0) {
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

      //    controller = new ProxyController(socket, player);
      //    controller.run();
    } else if (args.length == 2) {
      String host = args[0];
      int port;
      try {
        port = Integer.parseInt(args[1]);
      } catch (NumberFormatException e) {
        throw new RuntimeException("The port must be a number.");
      }

      try {
        Driver.runClient(host, port);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

  private static void runClient(String host, int port) throws IOException, IllegalStateException {
    Socket socket = new Socket(host, port);
    Player localPlayer = new AiPlayer();
    ProxyController proxyController = new ProxyController(socket, localPlayer);
    proxyController.run();
  }
}
