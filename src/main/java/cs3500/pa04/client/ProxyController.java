package cs3500.pa04.client;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.json.CoordinatesJson;
import cs3500.pa04.json.EndGameJson;
import cs3500.pa04.json.FleetJson;
import cs3500.pa04.json.JoinJson;
import cs3500.pa04.json.JsonUtils;
import cs3500.pa04.json.MessageJson;
import cs3500.pa04.json.SetupAdapter;
import cs3500.pa04.json.ShipAdapter;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.GameType;
import cs3500.pa04.model.Player;
import cs3500.pa04.model.Ship;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Dispatches messages and data from the server; responds to server
 */
public class ProxyController {
  private final Socket server;
  private final InputStream in;
  private final PrintStream out;
  private final Player player;
  private final ObjectMapper mapper = new ObjectMapper();

  /**
   * ProxyController for delegating and responding to server messages
   *
   * @param server The socket server
   * @param player A player
   * @throws IOException If something goes wrong with a stream
   */
  public ProxyController(Socket server, Player player) throws IOException {
    this.server = server;
    this.in = server.getInputStream();
    this.out = new PrintStream(server.getOutputStream());
    this.player = player;
  }

  /**
   * Runs the controller and gets the interactions from the server
   */
  public void run() {
    try {
      JsonParser jsonParser = this.mapper.getFactory().createParser(this.in);

      while (!this.server.isClosed()) {
        MessageJson message = jsonParser.readValueAs(MessageJson.class);
        this.delegateMessage(message);
      }

    } catch (IOException e) {
      throw new RuntimeException("Disconnected from server or failed to parse");
    }
  }

  /**
   * Delegates the arguments of the message
   *
   * @param message The deserialized message
   */
  public void delegateMessage(MessageJson message) {
    String methodName = message.methodName();
    JsonNode arguments = message.arguments();

    switch (methodName) {
      case "join" -> doJoin();
      case "setup" -> handleSetup(arguments);
      case "take-shots" -> doTakeShots();
      case "report-damage" -> handleDamageReport(arguments);
      case "successful-hits" -> handleSuccessfulHits(arguments);
      case "end-game" -> handleEndgame(arguments);
      default -> throw new IllegalStateException("Unknown server response");
    }
  }

  /**
   * Joins a game
   */
  private void doJoin() {
    JoinJson joinJson = new JoinJson(this.player.name(), GameType.SINGLE);
    JsonNode joinArgs = JsonUtils.serializeRecord(joinJson);
    MessageJson joinResponse = new MessageJson("join", joinArgs);
    this.out.println(JsonUtils.serializeRecord(joinResponse));
  }

  /**
   * Handles the setup from the server
   *
   * @param arguments The arguments that contain a width, height, and fleet-spec
   */
  private void handleSetup(JsonNode arguments) {
    SetupAdapter setupArgs = this.mapper.convertValue(arguments, SetupAdapter.class);
    List<Ship> fleet =
        this.player.setup(setupArgs.height(), setupArgs.width(), setupArgs.fleetSpec());

    List<ShipAdapter> adaptedFleet = new ArrayList<>();
    for (Ship ship : fleet) {
      adaptedFleet.add(new ShipAdapter(ship));
    }
    FleetJson fleetJson = new FleetJson(adaptedFleet);
    JsonNode fleetArgs = JsonUtils.serializeRecord(fleetJson);
    MessageJson fleetResponse = new MessageJson("setup", fleetArgs);
    this.out.println(JsonUtils.serializeRecord(fleetResponse));
  }

  /**
   * Takes shots from the local player
   */
  private void doTakeShots() {
    CoordinatesJson takeShotsCoordinates = new CoordinatesJson(this.player.takeShots());
    JsonNode coordinatesArgs = JsonUtils.serializeRecord(takeShotsCoordinates);
    MessageJson takeShotsResponse = new MessageJson("take-shots", coordinatesArgs);
    this.out.println(JsonUtils.serializeRecord(takeShotsResponse));
  }

  /**
   * Evaluates the coordinates from the server and responds with coordinates where there
   * were hits.
   *
   * @param arguments The coordinates
   */
  private void handleDamageReport(JsonNode arguments) {
    CoordinatesJson reportDamageArgs = this.mapper.convertValue(arguments, CoordinatesJson.class);
    List<Coord> damage = this.player.reportDamage(reportDamageArgs.coordinates());
    CoordinatesJson damageJson = new CoordinatesJson(damage);

    JsonNode reportDamageJson = JsonUtils.serializeRecord(damageJson);
    MessageJson reportDamageResponse = new MessageJson("report-damage", reportDamageJson);
    this.out.println(JsonUtils.serializeRecord(reportDamageResponse));
  }


  /**
   * Consumes server's coordinates of successful hits
   *
   * @param arguments The coordinates
   */
  private void handleSuccessfulHits(JsonNode arguments) {
    CoordinatesJson successfulHitsArgs = this.mapper.convertValue(arguments,
        CoordinatesJson.class);

    MessageJson successfulHitsResponse =
        new MessageJson("successful-hits", this.mapper.createObjectNode());
    this.out.println(JsonUtils.serializeRecord(successfulHitsResponse));
  }

  /**
   * Handles the endgame with the result and reason
   *
   * @param arguments Result and reason deserialized
   */
  private void handleEndgame(JsonNode arguments) {
    EndGameJson endGameArgs = this.mapper.convertValue(arguments, EndGameJson.class);
    try {
      this.server.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
