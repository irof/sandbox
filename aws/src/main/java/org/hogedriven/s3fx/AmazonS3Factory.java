package org.hogedriven.s3fx;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author irof
 */
public class AmazonS3Factory {

    public AmazonS3 createAmazonS3Client() throws IOException {
        Dialog<AmazonS3> dialog = new Dialog<>();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("s3config.fxml"));
        loader.setControllerFactory(clz -> new S3ConfigController(dialog));
        dialog.getDialogPane().setContent(loader.load());
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        return dialog.showAndWait().orElseThrow(() -> new RuntimeException("キャンセルしたので起動しません"));
    }

    static AmazonS3 createMock() {
        return (AmazonS3) Proxy.newProxyInstance(
                ClassLoader.getSystemClassLoader(),
                new Class[]{AmazonS3.class},
                createInvocationHandler());
    }

    private static InvocationHandler createInvocationHandler() {
        return (proxy, method, args) -> {
            System.out.printf("invoke: %s %s(%s)%n",
                    method.getReturnType().getSimpleName(), method.getName(), Arrays.toString(args));
            //TimeUnit.SECONDS.sleep(3);
            switch (method.getName()) {
                case "listBuckets":
                    return Arrays.asList(createBucket("hoge"), createBucket("fuga"), createBucket("piyo"));
                case "listObjects":
                    ObjectListing listing = new ObjectListing();
                    listing.getObjectSummaries().addAll(
                            Stream.generate(AmazonS3Factory::createS3ObjectSummary)
                                    .limit(20)
                                    .collect(Collectors.toList()));
                    return listing;
                case "listNextBatchOfObjects":
                    return new ObjectListing();
                case "getObjectMetadata":
                    return getObjectMetadata();
                case "createBucket":
                    return createBucket((String) args[0]);
                case "deleteBucket":
                case "putObject":
                case "deleteObject":
                    return null;
            }
            throw new UnsupportedOperationException(method.toString());
        };
    }

    private static ObjectMetadata getObjectMetadata() {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType("text/plane");
        return objectMetadata;
    }

    private static Bucket createBucket(String name) {
        Bucket bucket = new Bucket(name);
        bucket.setCreationDate(new Date());
        bucket.setOwner(new Owner("OwnerIdByMock", "OwnerDisplayNameByMock"));
        return bucket;
    }

    private static S3ObjectSummary createS3ObjectSummary() {
        S3ObjectSummary objectSummary = new S3ObjectSummary();
        objectSummary.setBucketName("S3ObjectSummaryBucketNameByMock");
        objectSummary.setKey("KeyByMock" + UUID.randomUUID());
        objectSummary.setLastModified(new Date());
        objectSummary.setSize(12345678);
        objectSummary.setStorageClass("S3ObjectSummaryStorageClassByMock");
        objectSummary.setETag("DUMMY ETag Value");
        return objectSummary;
    }
}
