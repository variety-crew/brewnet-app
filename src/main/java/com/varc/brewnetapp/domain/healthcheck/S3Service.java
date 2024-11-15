package com.varc.brewnetapp.domain.healthcheck;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
public class S3Service {

    private final AmazonS3 amazonS3;
    private final String bucketName;
    private static final String BUCKET_FOLDER = "brewnetapp";

    @Autowired
    public S3Service(String bucketName, AmazonS3 amazonS3) {
        this.bucketName = bucketName;
        this.amazonS3 = amazonS3;
    }

    public String upload(MultipartFile file) throws IOException {
        String fileName = BUCKET_FOLDER + "/" + UUID.randomUUID() + "_" + file.getOriginalFilename();
        String fileURL = "https://" + bucketName + ".s3.amazonaws.com/" + fileName;
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        // upload to S3

        amazonS3.putObject(bucketName, fileName, file.getInputStream(), metadata);
        return fileURL;

    }
}
