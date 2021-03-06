package com.springboot.service;

import com.springboot.dto.Result;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.nio.file.Path;

public interface FileUploadService {

    void init();

    Path loadFile(String filename);

    Resource loadAsResourceFile(String fileUuid);

    void deleteAll();

    Result storeFile(MultipartFile file, HttpSession session);

    String selectNameByUuid(String uuid);

}
