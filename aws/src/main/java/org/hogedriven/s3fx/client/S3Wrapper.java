package org.hogedriven.s3fx.client;

import com.amazonaws.services.s3.model.*;

import java.io.File;
import java.util.List;

/**
 * @author irof
 */
public interface S3Wrapper {
    List<Bucket> listBuckets();

    Bucket createBucket(String name);

    void deleteBucket(Bucket bucket);

    void putObject(Bucket bucket, String key, File srcFile);

    void deleteObject(S3ObjectSummary summary);

    List<S3ObjectSummary> listObjects(Bucket bucket);

    S3Object getObject(S3ObjectSummary summary);

    void getObject(S3ObjectSummary summary, File destFile);

    ObjectMetadata getObjectMetadata(S3ObjectSummary summary);
}
