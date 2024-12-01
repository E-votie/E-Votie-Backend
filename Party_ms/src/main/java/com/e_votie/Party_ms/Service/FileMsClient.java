package com.e_votie.Party_ms.Service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.e_votie.Party_ms.Config.FeignConfig;

@FeignClient(name = "FILE-MS", configuration = FeignConfig.class)
public interface FileMsClient {

    @GetMapping("/api/files/generatePresignedUrl")
    String getFileUrl(@RequestParam("fileName") String fileName);

    @PostMapping(value = "/api/files/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<String> uploadFile(@RequestPart("file") MultipartFile file);

    @GetMapping("/api/files/download/{fileName}")
    ResponseEntity<Resource> downloadFile(@PathVariable("fileName") String fileName);
}

