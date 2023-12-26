package com.products.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.products.service.UploadFileService;

import lombok.RequiredArgsConstructor;

@RestController("/api/v1/file")
@RequiredArgsConstructor
public class FilesController {
    
    private final UploadFileService uploadFileService;

    @PostMapping("/imagen")
    public ResponseEntity<Void> subirImagen(
        MultipartFile file,
        @RequestParam(required = true) String producto,
        @RequestParam(required = true) String version
    ){
        uploadFileService.subirArchivo(file, producto, version);
        return ResponseEntity.noContent().build();
    }
}
