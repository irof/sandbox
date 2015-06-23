package core;

import com.amazonaws.*;
import com.amazonaws.http.AmazonHttpClient;
import com.amazonaws.http.ExecutionContext;
import com.amazonaws.http.HttpResponse;
import com.amazonaws.http.HttpResponseHandler;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hogedriven.TemporaryServer;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Supplier;

/**
 * @author irof
 */
public class AmazonHttpClientTest {

    private final static Logger logger = LoggerFactory.getLogger("test");

    @Rule
    public TemporaryServer server = new TemporaryServer()
            .withSleep(2)
            .withBody(() -> "HOGEFUGAPIYO");

    @Test
    public void 同時接続数はAmazonHttpClientのインスタンス単位だよなぁという確認() throws Exception {
        ClientConfiguration config = new ClientConfiguration();
        // 同時接続数
        config.setMaxConnections(2);
        // リトライしない
        config.setMaxErrorRetry(0);
        // タイムアウトの時間
        config.setSocketTimeout(5_000);

        Supplier<AmazonHttpClient> client = clientSupplier(config);

        ExecutorService executor = Executors.newFixedThreadPool(10);
        List<Future<Void>> futures = executor.invokeAll(Arrays.asList(
                submit(client),
                submit(client),
                submit(client),
                submit(client)
        ));

        // 全部終わるの待つ
        for (Future<Void> future : futures) {
            future.get();
        }
    }

    /**
     * @param config 設定
     * @return テストで使用するクライアント提供オブジェクト
     */
    private Supplier<AmazonHttpClient> clientSupplier(ClientConfiguration config) {
        // 毎回クライアントを作るやつ
        //Supplier<AmazonHttpClient> client = () -> new AmazonHttpClient(config);

        // 一度作ったクライアントを共有するやつ
        AmazonHttpClient httpClient = new AmazonHttpClient(config);
        return () -> httpClient;
    }

    private Callable<Void> submit(Supplier<AmazonHttpClient> client) {
        return () -> {
            Request<?> request = new DefaultRequest<>("test-service");
            request.setEndpoint(server.getUri());
            InputStream in = new ByteArrayInputStream("hoge".getBytes(StandardCharsets.UTF_8));
            request.setContent(in);

            HttpResponseHandler<AmazonServiceException> errorResponse = null;
            ExecutionContext context = new ExecutionContext();

            client.get().execute(request, responseHandler(), errorResponse, context);
            return null;
        };
    }

    private HttpResponseHandler<AmazonWebServiceResponse<Object>> responseHandler() {
        return new HttpResponseHandler<AmazonWebServiceResponse<Object>>() {
            @Override
            public AmazonWebServiceResponse<Object> handle(HttpResponse response) throws Exception {
                return new AmazonWebServiceResponse<>();
            }

            @Override
            public boolean needsConnectionLeftOpen() {
                return false;
            }
        };
    }
}
