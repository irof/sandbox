package s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author irof
 */
public class S3Test {

    @Test
    public void listObjects() throws Exception {
        AmazonS3 client = new AmazonS3Client();
        List<Bucket> buckets = client.listBuckets();
        assertThat(buckets, notNullValue());
    }
}
