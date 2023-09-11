package com.teamchallenge.online_store.controller;

import com.teamchallenge.online_store.model.Image;
import com.teamchallenge.online_store.repository.ImageRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Part;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api/image")
@Tag(name = "Image")
public class ImageController {
    private final ImageRepository imageRepository;

    public ImageController(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @GetMapping("/")
    @Operation(summary = "Get image by id")
    private ResponseEntity<?> getImageById(@PathVariable Long id) {
        Image image = imageRepository.findById(id).orElse(null);
        assert image != null;
        return ResponseEntity.ok()
                .header("fileName", image.getOriginalFileName())
                .contentType(MediaType.valueOf(image.getContentType()))
                .contentLength(image.getSize())
                .body( new InputStreamResource(new ByteArrayInputStream(image.getBytes())));
    }

    @PostMapping("/upload")
    @Operation(summary = "Upload image")
    public ResponseEntity<String> uploadImage(@RequestParam("file") Part file) {
        try {
            if (file == null || file.getSize() == 0) {
                return ResponseEntity.badRequest().body("Please select a file to upload.");
            }

            String fileName = file.getSubmittedFileName();
            long fileSize = file.getSize();

            // Читаємо байти з InputStream і можемо зберегти їх у вашу систему.
            byte[] fileBytes = file.getInputStream().readAllBytes();

            // Ось де ви можете викликати ваш репозиторій для збереження об'єкту Image,
            // встановлюючи дані з файлу, такі як ім'я, розмір, тип тощо.
            Image image = new Image();
            image.setName(fileName);
            image.setOriginalFileName(fileName);
            image.setSize(fileSize);
            image.setContentType(file.getContentType());
            image.setBytes(fileBytes);

            // Зберігаємо об'єкт Image в репозиторії.
            imageRepository.save(image);

            return ResponseEntity.ok("File " + fileName + " uploaded successfully. File size: " + fileSize + " bytes.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file.");
        }
    }
}
