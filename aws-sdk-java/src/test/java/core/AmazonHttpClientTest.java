package core;

import com.amazonaws.*;
import com.amazonaws.http.AmazonHttpClient;
import com.amazonaws.http.ExecutionContext;
import com.amazonaws.http.HttpResponse;
import com.amazonaws.http.HttpResponseHandler;
import hogedriven.TemporaryServer;
import org.junit.ClassRule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * @author irof
 */
public class AmazonHttpClientTest {

    private final static Logger logger = LoggerFactory.getLogger("test");

    @ClassRule
    public static TemporaryServer server = new TemporaryServer(2);

    @Test
    public void 同時接続数はAmazonHttpClientのインスタンス単位だよなぁという確認() throws Exception {
        ClientConfiguration config = new ClientConfiguration();
        // 同時接続数
        config.setMaxConnections(2);
        // リトライしない
        config.setMaxErrorRetry(0);
        // タイムアウトの時間
        config.setSocketTimeout(30_000);

        Supplier<AmazonHttpClient> client = clientSupplier(config);

        long startTime = System.currentTimeMillis();
        List<CompletableFuture<Long>> results = Stream.generate(
                () -> sendRequestTask(client, startTime))
                .limit(5)
                .collect(toList());

        List<Long> times = results.stream().map(wrap(Future::get)).collect(toList());

        // 同時接続が2でsleep時間が2秒なので、こんな感じになってればOK。運任せだけど。
        assert times.stream().filter(t -> t < 3_000).count() == 2;
        assert times.stream().filter(t -> 3_000 < t && t < 5_000).count() == 2;
        assert times.stream().filter(t -> 5_000 < t).count() == 1;
    }

    private CompletableFuture<Long> sendRequestTask(Supplier<AmazonHttpClient> client, long startTime) {
        return CompletableFuture.runAsync(() -> sendRequest(client))
                .thenApply(v -> System.currentTimeMillis() - startTime);
    }

    private <T> Function<T, Long> wrap(ExFunction<T, Long> function) {
        return t -> {
            try {
                return function.apply(t);
            } catch (Exception e) {
                logger.warn("(-_-)", e);
                return Long.MIN_VALUE;
            }
        };
    }

    interface ExFunction<T, R> {
        R apply(T t) throws Exception;
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

    private void sendRequest(Supplier<AmazonHttpClient> client) {
        Request<?> request = new DefaultRequest<>("test-service");
        request.setEndpoint(server.getUri());
        InputStream in = new ByteArrayInputStream("hoge".getBytes(StandardCharsets.UTF_8));
        request.setContent(in);

        client.get().execute(request,
                new HttpResponseHandler<AmazonWebServiceResponse<Object>>() {
                    @Override
                    public AmazonWebServiceResponse<Object> handle(HttpResponse response) throws Exception {
                        return new AmazonWebServiceResponse<>();
                    }

                    @Override
                    public boolean needsConnectionLeftOpen() {
                        return false;
                    }
                },
                new HttpResponseHandler<AmazonServiceException>() {
                    @Override
                    public AmazonServiceException handle(HttpResponse response) throws Exception {
                        return null;
                    }

                    @Override
                    public boolean needsConnectionLeftOpen() {
                        return false;
                    }
                },
                new ExecutionContext());
    }
}
