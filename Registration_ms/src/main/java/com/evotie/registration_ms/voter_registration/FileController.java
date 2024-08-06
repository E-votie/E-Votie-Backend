package com.evotie.registration_ms.voter_registration;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.evotie.registration_ms.voter_registration.Service.S3Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;

import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/api/files")
public class FileController {
    @Autowired
    private S3Service s3Service;

    @Autowired
    private AmazonS3 amazonS3;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        log.info("Uploading file {} {}", file.getOriginalFilename(), file.getName());
        return ResponseEntity.ok(s3Service.uploadFile(file));
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        Resource resource = s3Service.downloadFile(fileName);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }

    @GetMapping("/generatePresignedUrl")
    public String generatePresignedUrl(@RequestParam String fileName) {
        return s3Service.generatePresignedUrlServise(fileName);
    }
}
