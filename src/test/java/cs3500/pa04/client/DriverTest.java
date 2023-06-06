package cs3500.pa04.client;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cs3500.pa04.client.Driver;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

/**
 * Tests the Driver
 */
class DriverTest {
  /**
   * The only way for the Driver to be tested fairly is through an error.
   * This works as intended if the error is thrown.
   */
  @Test
  void testMain() {
    OutputStream out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));

    assertDoesNotThrow(Driver::new);
    assertThrows(RuntimeException.class, () -> Driver.main(new String[] {}));

    String input = """
        EXIT 6
        1 2 2 1
        0 0
        0 1
        0 2
        0 3
        0 4
        0 5""";
    InputStream inStream = new ByteArrayInputStream(input.getBytes());
    assertDoesNotThrow(() -> System.setIn(inStream));
    assertDoesNotThrow(() -> Driver.main(new String[] {}));
  }
}