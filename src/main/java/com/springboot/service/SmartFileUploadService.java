package com.springboot.service;

import com.springboot.domain.Result;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.nio.file.Path;

public interface SmartFileUploadService {

    void init();

    Path loadFile(String filename);

    Resource loadAsResourceFile(String filename);

    void deleteAll();

    Result storeFile(MultipartFile file, HttpSession session);

}
