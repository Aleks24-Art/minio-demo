package com.minio.controller;

import io.minio.MinioClient;
import io.minio.RemoveObjectArgs;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@Slf4j
public class ObjectControllerDelete {

    @DeleteMapping("/v1/{bucket}/{key}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteObject1(@PathVariable String bucket, @PathVariable String key) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        MinioClient minioClient =
                MinioClient.builder()
                        .region("us-east-1")
                        .endpoint("localhost", 9001, false)
                        .credentials("minioadmin", "minioadmin")
                        .build();

        minioClient.removeObject(
                RemoveObjectArgs.builder()
                        .bucket(bucket)
                        .object(key)
                        .build()
        );
    }

    @DeleteMapping("/v2/{bucket}/{filename:.+}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteObject2(@PathVariable String bucket, @PathVariable String filename) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        MinioClient minioClient =
                MinioClient.builder()
                        .region("us-east-1")
                        .endpoint("localhost", 9001, false)
                        .credentials("minioadmin", "minioadmin")
                        .build();

        minioClient.removeObject(
                RemoveObjectArgs.builder()
                        .bucket(bucket)
                        .object(filename)
                        .build()
        );
    }
}
