package com.springboot.controller;

import com.springboot.domain.Result;
import com.springboot.service.SmartFileUploadService;
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

    private final SmartFileUploadService smartFileUploadService;

    @Autowired
    public SmartFileUploadController(SmartFileUploadService smartFileUploadService) {
        this.smartFileUploadService = smartFileUploadService;
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = smartFileUploadService.loadAsResourceFile(filename);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @PostMapping("/uploadFile")
    @ResponseBody
    public Result handleFileUpload(@RequestParam("file") MultipartFile file,
                                   Model model, HttpSession session) {
        return smartFileUploadService.storeFile(file,session);
    }

}
