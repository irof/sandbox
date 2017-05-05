package clients;

import okhttp3.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;
import java.util.StringJoiner;

public class OkHttpTest {

    public static void main(String[] args) throws IOException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("url");

        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
        String text = new StringJoiner("&")
                .add("test1=test")
                .add("test2=" + URLEncoder.encode("テストお", StandardCharsets.UTF_8.toString()))
                .toString();
        RequestBody body = RequestBody.create(mediaType, text);
        Request request = new Request.Builder()
                .url(resourceBundle.getString("url"))
                .post(body)
                .addHeader("X-CUSTOM", "VALUE1")
                .addHeader("X-CUSTOM", "VALUE2")
                .build();

        OkHttpClient client = new OkHttpClient();
        Response response = client.newCall(request).execute();
        String string = response.body().string();

        System.out.println(string);
    }
}
