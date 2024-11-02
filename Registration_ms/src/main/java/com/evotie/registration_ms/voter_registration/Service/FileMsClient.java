package com.evotie.registration_ms.voter_registration.Service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

@FeignClient(name = "FILE-MS")
public interface FileMsClient {

    @GetMapping("/api/files/generatePresignedUrl")
    String getFileUrl(@RequestParam("fileName") String fileName);

    @PostMapping("/api/files/upload")
    ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file);

    @GetMapping("/api/files/download/{fileName}")
    ResponseEntity<Resource> downloadFile(@PathVariable("fileName") String fileName);
}
