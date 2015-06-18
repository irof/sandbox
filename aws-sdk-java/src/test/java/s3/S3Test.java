package s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author irof
 */
public class S3Test {

    private static final Logger logger = LoggerFactory.getLogger(S3Test.class);
    private AmazonS3Client s3 = new AmazonS3Client();

    @Rule
    public TemporaryFolder temp = new TemporaryFolder();

    @Before
    public void setup() {
        // bucketがなかったら作る
        if (!s3.doesBucketExist("irof-sandbox")) {
            Bucket bucket = s3.createBucket("irof-sandbox");
            logger.info("create {}", bucket.getName());
        }
    }

    @Test
    public void listBuckets() throws Exception {
        List<Bucket> buckets = s3.listBuckets();
        assertThat(buckets, notNullValue());
    }

    @Test
    public void putWithFile() throws IOException {
        // Content-Typeはファイルの拡張子から判別される
        File file = temp.newFile("hoge.txt");
        try (OutputStream os = Files.newOutputStream(file.toPath())) {
            os.write("test".getBytes(StandardCharsets.UTF_8));
            os.flush();
        }

        // 適当に書き出したファイルをputする
        PutObjectRequest request = new PutObjectRequest("irof-sandbox", file.getName(), file);
        ObjectMetadata requestMetadata = new ObjectMetadata();
        requestMetadata.getUserMetadata().put("HOGE", "FUGA");
        request.setMetadata(requestMetadata);

        PutObjectResult result = s3.putObject(request);
        assertThat(result.getETag(), is("098f6bcd4621d373cade4e832627b4f6"));
        // ファイルの際に自動で計算される項目。Requestのmetaも更新される。
        // Content-Length
        // Content-Type
        // Content-MD5
        assertThat(requestMetadata.getContentLength(), is(4L));
        assertThat(requestMetadata.getContentType(), is("text/plain"));
        assertThat(requestMetadata.getContentMD5(), is("CY9rzUYh03PK3k6DJie09g=="));

        ObjectMetadata metadata = s3.getObjectMetadata("irof-sandbox", file.getName());
        assertThat(metadata.getContentLength(), is(4L));
        assertThat(metadata.getContentType(), is("text/plain"));
        // キー名は小文字になる
        assertThat(metadata.getUserMetaDataOf("hoge"), is("FUGA"));
    }

    @Test
    public void putWithInputStream() throws Exception {
        try (InputStream input = new ByteArrayInputStream("test".getBytes(StandardCharsets.UTF_8))) {
            ObjectMetadata metaData = new ObjectMetadata();

            // 可能なら設定する。設定しなかったらデフォルト値が使われる。
            metaData.setContentType("text/plain");

            // 設定しなくても計算されるぽいのでスルーでよさげ
            // metaData.setContentMD5("CY9rzUYh03PK3k6DJie09g==");

            // オプションだけど、できるかぎり設定する。
            // 設定しないとWARNログ出ちゃうし、違う値を設定すると例外出る。
            //metaData.setContentLength(3);

            PutObjectRequest request = new PutObjectRequest("irof-sandbox", "byteFile.txt", input, metaData);
            PutObjectResult result = s3.putObject(request);

            assertThat(result.getETag(), is("098f6bcd4621d373cade4e832627b4f6"));
        }
    }
}
