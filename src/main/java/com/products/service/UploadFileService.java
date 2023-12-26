package com.products.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.products.error.FileError;
import com.products.utils.Util;

@Component
public class UploadFileService {

    public void subirArchivo(MultipartFile file, String producto, String version) {
        try {
            String filename = UUID.randomUUID().toString();
            byte[] bytes = file.getBytes();
            String fileOriginalName = file.getOriginalFilename();

            long fileSize = file.getSize();
            long maxSize = 5 * 1024 * 1024;
            if(fileSize > maxSize)
                throw new FileError(null, "El archivo no debe pesar mas de 5MB");

            if(
                !fileOriginalName.endsWith(".jpg") &&
                !fileOriginalName.endsWith(".jpeg") &&
                !fileOriginalName.endsWith(".png") &&
                !fileOriginalName.endsWith(".webp")
            )
                throw new FileError(null, "El archivo debe ser de formato '.png', '.jpg', '.jpeg' o '.webp'");

            String fileExtension = fileOriginalName.substring(fileOriginalName.lastIndexOf("."));
            String newFileName = Util.formatTitle(version) + Util.formatTitle(version) + filename + fileExtension;

            File folder = new File("src/main/resources/images");
            if(!folder.exists()){
                folder.mkdirs();
            }

            Path path = Paths.get("src/main/resources/images/" + newFileName);
            Files.write(path, bytes);
        } catch (IOException e) {
            throw new FileError(producto, "Ocurrio un error al intentar subir el archivo.");
        }
    }
    
}
