package hogedriven;

import org.junit.rules.ExternalResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
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

/**
 * @author irof
 */
public class TemporaryServer extends ExternalResource {

    private static final Logger logger = LoggerFactory.getLogger(TemporaryServer.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss zzz", Locale.US);

    private ServerSocket socket;
    private ExecutorService serverService;

    ExecutorService executorService;

    private final int sleep;
    private final Supplier<String> bodySupplier = () -> "DUMMY";

    public TemporaryServer(int sleep) {
        this.sleep = sleep;
    }

    @Override
    protected void before() throws Throwable {
        // 空いてるポートで作る
        socket = new ServerSocket(0);

        // リクエストをアレするスレッド
        executorService = Executors.newCachedThreadPool();

        // サーバー本体のスレッド
        serverService = Executors.newSingleThreadExecutor();
        serverService.submit(() -> {
            while (!socket.isClosed()) {
                handle(socket.accept());
            }
            return null;
        });

        logger.info("Server initialized {}", getUri());
    }

    private void handle(Socket socket) throws Exception {
        logger.info("accepted: {}", socket);
        // 別スレッドで実行する
        executorService.submit(() -> {
                    logger.info("sleep {} seconds.", sleep);
                    TimeUnit.SECONDS.sleep(sleep);

                    OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());
                    writeResponse(writer);
                    writer.flush();
                    socket.close();
                    return null;
                }
        );
    }

    private void writeResponse(OutputStreamWriter writer) throws Exception {
        String body = bodySupplier.get();
        writer.write("HTTP/1.1 200 OK\n");

        writer.write("Connection: close\n");
        writer.write("Date: " + ZonedDateTime.now(ZoneId.of("GMT")).format(formatter) + "\n");

        writer.write("Server: " + this.getClass().getName() + "\n");

        writer.write("Content-Length: " + body.getBytes(StandardCharsets.UTF_8).length + "\n");
        writer.write("Content-Type: text/plain; charset=UTF-8\n");

        writer.write("\n");

        writer.write(body);
    }

    @Override
    protected void after() {
        try {
            socket.close();
        } catch (IOException e) {
            logger.error(e.toString());
        }
        serverService.shutdown();
        executorService.shutdown();
    }

    public int getPort() {
        return socket.getLocalPort();
    }

    public URI getUri() {
        try {
            return new URI("http://localhost:" + getPort());
        } catch (URISyntaxException e) {
            logger.error(e.toString());
            throw new AssertionError(e);
        }
    }
}
