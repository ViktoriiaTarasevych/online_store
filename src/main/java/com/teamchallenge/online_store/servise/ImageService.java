package com.teamchallenge.online_store.servise;

import com.teamchallenge.online_store.model.Image;
import com.teamchallenge.online_store.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ImageService {
    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Image saveImage(Image image) {
        return imageRepository.save(image);
    }

    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    public Image getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Зображення не знайдено"));
    }

    public void deleteImage(Long id) {
        imageRepository.deleteById(id);
    }
}