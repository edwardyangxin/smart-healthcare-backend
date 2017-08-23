package com.springboot.service;

import com.springboot.dto.updto.ControllerResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    void init();

    ControllerResponse store(MultipartFile file,HttpSession session);

    Stream<Path> loadAll();

    Path loadPicture(String pictureName);

    Path loadFile(String filename);

    Resource loadAsResource(String filename);

    Resource loadAsResourceFile(String filename);

    void deleteAll();

    ControllerResponse storeFile(MultipartFile file,HttpSession session);
    //void deleteFile(Path path);
    // void updatePicture(TpFile file);
    //  TpFile findFile(String name);
    // ControllerResponse checkFormat(MultipartFile file);

}
