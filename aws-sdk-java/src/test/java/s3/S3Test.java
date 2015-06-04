package s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author irof
 */
public class S3Test {

    private static final Logger logger = LoggerFactory.getLogger(S3Test.class);
    private AmazonS3Client s3 = new AmazonS3Client();

    @Test
    public void listBuckets() throws Exception {
        List<Bucket> buckets = s3.listBuckets();
        assertThat(buckets, notNullValue());
    }
}
