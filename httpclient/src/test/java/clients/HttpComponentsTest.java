package clients;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HttpComponentsTest {

    public static void main(String[] args) throws IOException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("url");

        HttpPost httpPost = new HttpPost(resourceBundle.getString("url"));
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("test1", "value"));
        nvps.add(new BasicNameValuePair("test2", "テストテスト"));
        httpPost.setHeader(h
        );
        httpPost.setEntity(new UrlEncodedFormEntity(nvps));

        CloseableHttpClient httpclient = HttpClients.createDefault();
        try (CloseableHttpResponse response2 = httpclient.execute(httpPost)) {
            System.out.println(response2.getStatusLine());
            HttpEntity entity2 = response2.getEntity();

            EntityUtils.consume(entity2);
        }
    }
}
