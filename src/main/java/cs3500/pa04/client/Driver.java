package cs3500.pa04.client;

import cs3500.pa04.controller.BattleSalvoController;
import cs3500.pa04.model.AiPlayer;
import cs3500.pa04.model.BetterAiPlayer;
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
        System.err.println("An unexpected error occurred.");
      }
    } else if (args.length == 2) {
      String host = args[0];
      int port;
      try {
        port = Integer.parseInt(args[1]);
      } catch (NumberFormatException e) {
        System.err.println("The port must be a number.");
        return;
      }

      try {
        Driver.runClient(host, port);
      } catch (IOException | IllegalStateException e) {
        System.err.println(e.getMessage());
      }
    }
  }

  private static void runClient(String host, int port) throws IOException, IllegalStateException {
    Socket socket = new Socket(host, port);
    Player localPlayer = new BetterAiPlayer();
    ProxyController proxyController = new ProxyController(socket, localPlayer);
    proxyController.run();
  }
}
