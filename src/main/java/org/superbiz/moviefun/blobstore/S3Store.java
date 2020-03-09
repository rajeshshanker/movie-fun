package org.superbiz.moviefun.blobstore;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.apache.tika.Tika;
import org.apache.tika.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;

public class S3Store implements BlobStore {

    private final AmazonS3Client amazonS3Client;
    private final String storageBucket;
    private final Tika tika;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public S3Store(AmazonS3Client s3Client, String photoStorageBucket) {
        this.tika = new Tika();
        this.amazonS3Client = s3Client;
        this.storageBucket = photoStorageBucket;
    }

    @Override
    public void put(Blob blob) throws IOException {
        logger.info("Putting Object cover into S3Store :");
        amazonS3Client.putObject(storageBucket,blob.getName(),blob.getInputStream(),new ObjectMetadata());
    }

    @Override
    public Optional<Blob> get(String name) throws IOException {
        logger.info("Getting Object cover from S3Store :");
        if (!amazonS3Client.doesObjectExist(storageBucket, name)) {
            logger.info("Found to be empty Object from S3Store :");
            return Optional.empty();

        }
       try (S3Object s3Object = amazonS3Client.getObject(storageBucket, name)) {
         S3ObjectInputStream content = s3Object.getObjectContent();
        byte[] bytes = IOUtils.toByteArray(content);
        return Optional.of(new Blob(
                  name,
                  new ByteArrayInputStream(bytes),
                   tika.detect(bytes)

            ));

        }

    }

    @Override
    public void deleteAll() {
        amazonS3Client.deleteBucket(storageBucket);

    }

}
