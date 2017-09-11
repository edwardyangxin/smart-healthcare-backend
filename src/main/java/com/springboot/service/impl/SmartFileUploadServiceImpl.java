package com.springboot.service.impl;

import com.springboot.controller.FileUploadController;
import com.springboot.domain.Result;
import com.springboot.domain.UploadFile;
import com.springboot.enums.ResultEnum;
import com.springboot.exception.storage.StorageException;
import com.springboot.exception.storage.StorageFileNotFoundException;
import com.springboot.mapper.SmartFileUploadMapper;
import com.springboot.service.SmartFileUploadService;
import com.springboot.tools.ResultUtil;
import com.springboot.tools.UUIDTool;
import com.springboot.uploadDir.StorageProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

@Slf4j
@Service
public class SmartFileUploadServiceImpl implements SmartFileUploadService {

    @Value("${smart.upload.filePath}")
    private String filePath;
    private final Path rootFileLocation;
    private SmartFileUploadMapper smartFileUploadMapper;


    @Autowired
    public SmartFileUploadServiceImpl(StorageProperties properties, SmartFileUploadMapper smartFileUploadMapper) {
        this.rootFileLocation = Paths.get(properties.getFilelocation());
        this.smartFileUploadMapper = smartFileUploadMapper;
    }

    @Override
    public Result storeFile(MultipartFile file, HttpSession session) {
        Result checkFormatResult = checkFormat(file);
        if (!checkFormatResult.getABoolean()) {
            return checkFormatResult;
        }
   /*     if (!ImageFormat(file)) {
            log.info("上传的文件不是图片格式！");
            return ResultUtil.error(ResultEnum.file_picture_error);
        }*/
        Result copyFileResult = copyFile(file);
        if (!copyFileResult.getABoolean()) {
            return copyFileResult;
        }
        return insertFile(file.getOriginalFilename());
    }


    public Result insertFile(String fileName) {
        UploadFile uploadFile = new UploadFile();
        uploadFile.setFileName(fileName);
        uploadFile.setFilePath(filePath);
        smartFileUploadMapper.insertUploadFile(uploadFile);
        return ResultUtil.success(ResultEnum.file_upload_success);
    }

    public Result renameFile(MultipartFile file) {
        String temName = UUIDTool.getUuid() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        File oldFile = new File("upload-dir" + File.separator + "file" + File.separator + file.getOriginalFilename());
        File newFile = new File("upload-dir" + File.separator + "file" + File.separator + temName);
        try {
            boolean flag = oldFile.renameTo(newFile);
            if (flag) {
            } else {
                log.info("RenameToFile faild！");
                return ResultUtil.error(ResultEnum.file_storage_error);
            }
        } catch (SecurityException e) {
            log.info("RenameToFile faild！");
            return ResultUtil.error(ResultEnum.file_storage_error);
        }
        return ResultUtil.success();
    }


    public Result copyFile(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), this.rootFileLocation.resolve(file.getOriginalFilename()));
        } catch (FileAlreadyExistsException e) {
            log.info("File already exist！");
            return ResultUtil.error(ResultEnum.file_exist_error);
        } catch (IOException e) {
            log.info("Storage file failed！");
            return ResultUtil.error(ResultEnum.file_storage_error);
        }
        log.info("文件保存成功！");
        return ResultUtil.success();
    }

    public Result checkFormat(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                log.info("文件(内容)为空！");
                return ResultUtil.error(ResultEnum.file_empty_error);
            }
            String suffixName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            log.info("文件格式有误！");
            return ResultUtil.error(ResultEnum.file_format_error);
        }
        return ResultUtil.success();
    }


    public URI uploadedFileUrl(Path path) {
        UriComponents uriComponents = MvcUriComponentsBuilder
                .fromMethodName(FileUploadController.class, "serveFile", path.getFileName().toString()).build();
        return uriComponents.encode().toUri();
    }

    @Override
    public Path loadFile(String fileName) {
        return rootFileLocation.resolve(fileName);
    }


    @Override
    public Resource loadAsResourceFile(String filename) {
        try {
            Path file = loadFile(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                log.info("Could not read file: " + filename);
                throw new StorageFileNotFoundException("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            log.info("Could not read file: " + filename);
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootFileLocation.toFile());
    }

    public void deleteFile(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            log.info(e.getMessage());
        }
    }

    @Override
    public void init() {
        try {
            Files.createDirectory(rootFileLocation);
        } catch (IOException e) {
            log.info("初始化存储失败！");
            throw new StorageException("初始化存储失败！", e);
        }
    }

    public boolean ImageFormat(MultipartFile file) {
        try {
            ImageInputStream iis = ImageIO.createImageInputStream(file.getInputStream());
            Iterator<ImageReader> iterator = ImageIO.getImageReaders(iis);
            if (iterator.hasNext()) {
                return true;
            } else {
                iis.close();
                return false;
            }
        } catch (IllegalArgumentException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }
}
 /*
    public TpFile findFile(String name) {
        return smartFileUploadMapper.findFile(name);
    }

    Result renameFileResult = renameFile(file);
        if(!renameFileResult.getABoolean())

    {
        return renameFileResult;
    }

    String name = smartFileUploadMapper.findFile(session.getAttribute("uuid").toString()).getFileName();
       if(name !=null)

    {
        deleteFile(loadFile(name));
    }*/