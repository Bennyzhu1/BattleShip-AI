package cs3500.pa04.client;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.json.EndGameJson;
import cs3500.pa04.json.MessageJson;
import cs3500.pa04.json.SetupAdapter;
import cs3500.pa04.json.ReportDamageJson;
import cs3500.pa04.json.SuccessfulHitsJson;
import cs3500.pa04.model.Player;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Dispatches messages and data from the server; responds to server
 */
public class ProxyController {
  private final Socket server;
  private final InputStream in;
  private final PrintStream out;
  private final Player player;
  private final ObjectMapper mapper = new ObjectMapper();

  public ProxyController(Socket server, Player player) throws IOException {
    this.server = server;
    this.in = server.getInputStream();
    this.out = new PrintStream(server.getOutputStream());
    this.player = player;
  }

  public void run() {
    try {
      JsonParser jsonParser = this.mapper.getFactory().createParser(this.in);

      while (!this.server.isClosed()) {
        MessageJson message = jsonParser.readValueAs(MessageJson.class);
        // TODO: Delegate messages
      }

    } catch (IOException e) {
      throw new RuntimeException("Disconnected from server or failed to parse");
    }
  }

  public void delegateMessage(MessageJson message) {
    String methodName = message.methodName();
    JsonNode arguments = message.arguments();

    switch (methodName) {
      case "join" -> doJoin();
      case "setup" -> handleSetup(arguments);
      case "take-shots" -> doTakeShots();
      case "report-damage" -> handleReport(arguments);
      case "successful-hits" -> handleSuccessfulHits(arguments);
      case "end-game" -> handleEndgame(arguments);
    }
  }

  private void doJoin() {
    // serialize a join record
    // then send it to the server
    // this.out.println works
    // this.out.write(some_string.getBytes())
  }

  private void handleSetup(JsonNode arguments) {
    SetupAdapter setupArgs = this.mapper.convertValue(arguments, SetupAdapter.class);
    // setupArgs.height, .width, .fleetSpec
  }

  private void doTakeShots() {
    // Let the client take shots
  }

  private void handleReport(JsonNode arguments) {
    ReportDamageJson reportDamageArgs = this.mapper.convertValue(arguments, ReportDamageJson.class);
  }


  private void handleSuccessfulHits(JsonNode arguments) {
    SuccessfulHitsJson successfulHitsArgs = this.mapper.convertValue(arguments,
        SuccessfulHitsJson.class);
  }

  private void handleEndgame(JsonNode arguments) {
    EndGameJson endGameArgs = this.mapper.convertValue(arguments, EndGameJson.class);
  }

}
