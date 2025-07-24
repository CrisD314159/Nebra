package com.crisdevApps.Nebra.services.interfaces;

import com.crisdevApps.Nebra.model.Image;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public interface IImageService {

    Image UploadImage(MultipartFile image);
    List<Image> UploadSeveralImages(List<MultipartFile> images);
    void DeleteSeveral(List<Image> images);
    void DeleteImage(String imageId);
    Image GetImage(String image);
}
