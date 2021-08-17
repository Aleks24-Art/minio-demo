package com.minio.controller;

import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.UploadObjectArgs;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@Slf4j
@RestController
public class ObjectControllerPut {

    /*MultipartException: Current request is not a multipart request*/
    @PostMapping(value = "/v1/{bucket}/{key}")
    @ResponseStatus(HttpStatus.CREATED)
    public void putObject1(
            @PathVariable String bucket,
            @PathVariable String key,
            @RequestPart MultipartFile file) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, InternalException, XmlParserException {


        MinioClient minioClient =
                MinioClient.builder()
                        .endpoint("http://127.0.0.1:9001")
                        .credentials("minioadmin", "minioadmin")
                        .build();

        ObjectWriteResponse objectWriteResponse = minioClient.putObject(
                PutObjectArgs.builder()
                        //.headers(Map.of("ETag", "sdsa\""))
                        .bucket(bucket)
                        .object(key)
                        .region("us-east-1")
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .build()
        );

        log.info("Step after build");
    }

    @PostMapping(value = "/v2/{bucket}/{key}")
    @ResponseStatus(HttpStatus.CREATED)
    public void putObject2(
            @PathVariable String bucket,
            @PathVariable String key,
            HttpServletRequest request) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, InternalException, XmlParserException {

        try(ServletInputStream inputStream = request.getInputStream()) {

            var responseHeaders = new HttpHeaders();
            responseHeaders.set("x-amz-id-2", "LriYPLdmOdAiIfgSm/F1YsViT1LW94/xUQxMsF7xiEb1a0wiIOIxl+zbwZ163pt7");
            responseHeaders.set("x-amz-request-id", "1691F1D81D1D7F10");
            responseHeaders.set("ETag", "627e865bbde56c48596452fdc161e927");

            byte[] buf = inputStream.readAllBytes();
            ByteArrayInputStream array = new ByteArrayInputStream(buf);

            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint("http://127.0.0.1:9001")
                            .credentials("minioadmin", "minioadmin")
                            .build();

            ObjectWriteResponse objectWriteResponse = minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .headers(responseHeaders.toSingleValueMap())
                            .object(key)
                            .region("us-east-1")
                            .stream(array, buf.length, -1)
                            .build()
            );

        }
        log.info("Step after build");
    }


    @PostMapping(value = "/v3/{bucket}/{key}" )
    @ResponseStatus(HttpStatus.CREATED)
    public void putObjectFromFile(
            @PathVariable String bucket, @PathVariable String key
            ) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, InternalException, XmlParserException {

        MinioClient minioClient =
                MinioClient.builder()
                        .endpoint("http://127.0.0.1:9001")
                        .credentials("minioadmin", "minioadmin")
                        .build();

        ObjectWriteResponse objectWriteResponse = minioClient.uploadObject(
                UploadObjectArgs.builder()
                        .bucket("asiatrip")
                        .headers(Map.of("ETag", "f88dd058fe004909615a64f01be66a7"))
                        .region("us-east-1")
                        .object("myobject")
                        .contentType("application/octet-stream")
                        .filename("src/main/resources/person.json").build());


        log.info(objectWriteResponse.bucket());
    }
}











