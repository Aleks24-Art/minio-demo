package com.minio.controller;

import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.MinioClient;
import io.minio.errors.*;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class ObjectControllerGet {

    @GetMapping("/v1/{bucket}/{key}")
    public ResponseEntity<ByteArrayResource> getObject1(
            @PathVariable String bucket,
            @PathVariable String key) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

        MinioClient minioClient =
                MinioClient.builder()
                        .region("us-east-1")
                        .endpoint("localhost", 9001, false)
                        .credentials("minioadmin", "minioadmin")
                        .build();

        GetObjectResponse object = minioClient.getObject(
                GetObjectArgs.builder()
                        .object(key)
                        .bucket(bucket)
                        .build()
        );

        return ResponseEntity.ok().body(new ByteArrayResource(object.readAllBytes()));
    }

}
