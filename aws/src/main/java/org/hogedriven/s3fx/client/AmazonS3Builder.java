package org.hogedriven.s3fx.client;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author irof
 */
public class AmazonS3Builder {

    private String proxyText;
    private AWSCredentials credentials;

    private Consumer<AmazonS3> verifier;
    private boolean readOnly = false;

    public S3Wrapper build() {
        AmazonS3Client client = new AmazonS3Client(credentials, createClientConfig());
        if (verifier != null) verifier.accept(client);
        return new S3WrapperImpl(client, readOnly);
    }

    private InvocationHandler createHandler(AmazonS3Client client) {
        return new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return method.invoke(client, args);
            }
        };
    }

    private ClientConfiguration createClientConfig() {
        ClientConfiguration config = new ClientConfiguration();
        Pattern pattern = Pattern.compile("(.+):(\\d+)");
        Matcher matcher = pattern.matcher(proxyText);
        if (matcher.matches()) {
            config.setProxyHost(matcher.group(1));
            config.setProxyPort(Integer.valueOf(matcher.group(2)));
        }
        return config;
    }

    public AmazonS3Builder withProxy(String text) {
        proxyText = text;
        return this;
    }

    public AmazonS3Builder verify(Consumer<AmazonS3> verifier) {
        this.verifier = verifier;
        return this;
    }

    public AmazonS3Builder basic(String accessKey, String secretKey) {
        credentials = new BasicAWSCredentials(accessKey, secretKey);
        return this;
    }

    public AmazonS3Builder defaultProfile() {
        credentials = new ProfileCredentialsProvider().getCredentials();
        return this;
    }

    public AmazonS3Builder readOnlyLock(boolean flg) {
        this.readOnly = flg;
        return this;
    }
}
