package com.springboot.controller;

import com.springboot.dto.updto.ControllerResponse;
import com.springboot.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@RequestMapping(value = "/smart")
@Controller
public class SmartFileUploadController {

    private final StorageService storageService;

    @Autowired
    public SmartFileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/pictures/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> servePicture(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResourceFile(filename);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
    @PostMapping("/uploadPicture")
    @ResponseBody
    public ControllerResponse handlePictureUpload(@RequestParam("file") MultipartFile file,
                                                  Model model,HttpSession session) {
        return storageService.store(file,session);
    }

    @PostMapping("/uploadFile")
    @ResponseBody
    public ControllerResponse handleFileUpload(@RequestParam("file") MultipartFile file,
                                               Model model,HttpSession session) {
        return storageService.storeFile(file,session);
    }
}
