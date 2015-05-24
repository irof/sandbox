package org.hogedriven.client;

import com.amazonaws.services.s3.model.S3ObjectSummary;

import java.util.Objects;

/**
 * @author irof
 */
public class S3ObjectIdentifier {
    private final String bucket;
    private final String key;

    public S3ObjectIdentifier(S3ObjectSummary summary) {
        this(summary.getBucketName(), summary.getKey());
    }

    public S3ObjectIdentifier(String bucket, String key) {
        this.bucket = bucket;
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        S3ObjectIdentifier that = (S3ObjectIdentifier) o;
        return Objects.equals(bucket, that.bucket) &&
                Objects.equals(key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bucket, key);
    }
}
