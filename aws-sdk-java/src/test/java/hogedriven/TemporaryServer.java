package hogedriven;

import org.junit.rules.ExternalResource;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.logging.Logger;

/**
 * @author irof
 */
public class TemporaryServer extends ExternalResource {

    private static final Logger logger = Logger.getLogger(TemporaryServer.class.getName());

    private ServerSocket socket;
    private ExecutorService service;
    private int seconds = 0;
    private Supplier<String> bodySupplier = () -> "DUMMY";

    public TemporaryServer withSleep(int seconds) {
        this.seconds = seconds;
        return this;
    }

    public TemporaryServer withBody(Supplier<String> bodySupplier) {
        this.bodySupplier = bodySupplier;
        return this;
    }

    @Override
    protected void before() throws Throwable {
        // 空いてるポートで作る
        socket = new ServerSocket(0);
        service = Executors.newSingleThreadExecutor();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss zzz", Locale.US);

        service.submit(() -> {
            while (!socket.isClosed()) {

                try (Socket s = socket.accept()) {
                    TimeUnit.SECONDS.sleep(seconds);

                    String body = bodySupplier.get();
                    OutputStream outputStream = s.getOutputStream();
                    OutputStreamWriter writer = new OutputStreamWriter(outputStream);
                    writer.write("HTTP/1.1 200 OK\n");

                    writer.write("Connection: close\n");
                    writer.write("Date: " + ZonedDateTime.now(ZoneId.of("GMT")).format(formatter) + "\n");

                    writer.write("Server: " + this.getClass().getName() + "\n");

                    writer.write("Content-Length: " + body.getBytes(StandardCharsets.UTF_8).length + "\n");
                    writer.write("Content-Type: text/plain; charset=UTF-8\n");

                    writer.write("\n");

                    writer.write(body);
                    writer.flush();
                    outputStream.flush();
                }
            }
            // Callableにするため
            return null;
        });
    }

    @Override
    protected void after() {
        try {
            socket.close();
        } catch (IOException e) {
            logger.severe(e.toString());
        }
        service.shutdown();
    }

    public int getPort() {
        return socket.getLocalPort();
    }

    public URI getUri() {
        try {
            return new URI("http://localhost:" + getPort());
        } catch (URISyntaxException e) {
            logger.severe(e.toString());
            throw new AssertionError(e);
        }
    }
}
