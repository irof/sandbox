package s3;

import com.amazonaws.event.ProgressEvent;
import com.amazonaws.event.ProgressListener;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.internal.Constants;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerConfiguration;
import com.amazonaws.services.s3.transfer.Upload;
import com.amazonaws.services.s3.transfer.model.UploadResult;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.assertTrue;

/**
 * @author irof
 */
public class S3MultipartUploadTest {

    private static final Logger logger = LoggerFactory.getLogger(S3MultipartUploadTest.class);

    @Test
    public void 高レベルAPIでマルチパートアップロードする() throws Exception {
        TransferManager transferManager = new TransferManager();
        TransferManagerConfiguration configuration = new TransferManagerConfiguration();
        // パートのサイズ: 5MB-5GB, default: 5MB
        configuration.setMinimumUploadPartSize(5 * Constants.MB);
        // 閾値: デフォルト16MB
        configuration.setMultipartUploadThreshold(5 * Constants.MB);
        transferManager.setConfiguration(configuration);

        // アップロードするデータを雑に用意（閾値より大きいの）
        byte[] bytes = new byte[5 * Constants.MB + 1];

        // 渡すものは通常のputObjectと同じ
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentLength(bytes.length);
        PutObjectRequest request = new PutObjectRequest(
                "irof-sandbox", "S3MultipartUpload/hi-level-api.dat",
                new ByteArrayInputStream(bytes), meta);
        // アップロード実行
        Upload upload = transferManager.upload(request);

        // 完了まで待つ
        UploadResult result = upload.waitForUploadResult();

        // ETagは末尾に "-2" がつく
        logger.info(result.getETag());
        assertTrue(result.getETag().endsWith("-2"));
    }

    @Ignore
    @Test
    public void 比較用の通常のputObject() throws Exception {
        byte[] bytes = new byte[5 * Constants.MB + 1];
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentLength(bytes.length);
        PutObjectRequest putObjectRequest
                = new PutObjectRequest("irof-sandbox", "S3MultipartUpload/basic-5MB.dat",
                new ByteArrayInputStream(bytes), meta);
        PutObjectResult result = new AmazonS3Client().putObject(putObjectRequest);
        logger.info(result.getETag());
    }
}
