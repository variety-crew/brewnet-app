package com.varc.brewnetapp.domain.healthcheck;

import com.varc.brewnetapp.common.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("api/v1/check")
public class CheckController {

    private final S3Service s3Service;

    public CheckController(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @GetMapping
    public String check() {
        return "Good";
    }

    @PostMapping("/images/upload")
    public ResponseEntity<ResponseMessage<String>> uploadImage(
            @RequestPart(value = "images", required = false) MultipartFile file
    ) throws IOException {
        String fileURL = s3Service.upload(file);
        return ResponseEntity.ok(new ResponseMessage<>(HttpStatus.SC_OK, "upload successful", fileURL));
    }

}
