package com.springboot.controller;

import com.springboot.dto.Result;
import com.springboot.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

@RequestMapping(value = "/TM")
@Controller
public class FileUploadController {

    private final FileUploadService smartFileUploadService;

    @Autowired
    public FileUploadController(FileUploadService smartFileUploadService) {
        this.smartFileUploadService = smartFileUploadService;
    }

    @GetMapping("/files/{fileUuid:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String fileUuid)  throws UnsupportedEncodingException{
        Resource file = smartFileUploadService.loadAsResourceFile(fileUuid);
        String realName = smartFileUploadService.selectNameByUuid(fileUuid);

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + new String(realName.getBytes(),"iso-8859-1") + "\"")
                .body(file);
    }

    @PostMapping("/uploadFile")
    @ResponseBody
    public Result handleFileUpload(@RequestParam("file") MultipartFile file,
                                   Model model, HttpSession session) {
        return smartFileUploadService.storeFile(file,session);
    }

}
