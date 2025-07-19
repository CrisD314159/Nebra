package com.crisdevApps.Nebra.services.implementations;

import com.crisdevApps.Nebra.exceptions.EntityNotFoundException;
import com.crisdevApps.Nebra.exceptions.UnexpectedException;
import com.crisdevApps.Nebra.model.Image;
import com.crisdevApps.Nebra.repositories.ImageRepository;
import com.crisdevApps.Nebra.services.interfaces.IImageService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {

    private final Cloudinary cloudinary;
    private final ImageRepository imageRepository;

    @Override
    public Image UploadImage(MultipartFile image) {
        try{
            HashMap<Object, Object> options = new HashMap<>();
            options.put("folder", "nebraImages");
            Map uploadedFile = cloudinary.uploader().upload(image.getBytes(), options);
            String publicId = (String) uploadedFile.get("public_id");
            String publicUrl = cloudinary.url().secure(true).generate(publicId);

            Image imageObject = Image.builder().id(publicId).link(publicUrl).build();
            imageRepository.save(imageObject);

            return imageObject;
        } catch (IOException e) {
            throw new UnexpectedException("Failed to upload image: " + e.getMessage());
        }
    }

    @Override
    public List<Image> UploadSeveralImages(List<MultipartFile> images) {
        return images.stream().map(this::UploadImage).toList();
    }

    @Override
    public void DeleteSeveral(List<Image> images) {
        images.forEach(image -> DeleteImage(image.getId()));
    }

    @Override
    public void DeleteImage(String imageId) {
        try{
            Optional<Image> imageOptional = imageRepository.findById(imageId);
            if (imageOptional.isEmpty()) throw new EntityNotFoundException("Image not found");
            Image image = imageOptional.get();
            cloudinary.uploader().destroy(imageId, ObjectUtils.emptyMap());
            imageRepository.delete(image);
        } catch (IOException e) {
            throw new UnexpectedException("Failed to upload image: " + e.getMessage());
        }

    }
}
