package com.springboot.service.impl;

import com.springboot.controller.FileUploadController;
import com.springboot.domain.TpFile;
import com.springboot.dto.updto.ControllerResponse;
import com.springboot.exception.storage.StorageException;
import com.springboot.exception.storage.StorageFileNotFoundException;
import com.springboot.mapper.UploadMapper;
import com.springboot.service.SmartFileUploadService;
import com.springboot.tools.UUIDTool;
import com.springboot.uploadDir.StorageProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.stream.Stream;

@Slf4j
@Service
public class SmartFileUploadServiceImpl implements SmartFileUploadService{

    private final Path rootLocation;
    private final Path rootFileLocation;
    private UploadMapper uploadMapper;


    @Autowired
    public SmartFileUploadServiceImpl(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
        this.rootFileLocation = Paths.get(properties.getFilelocation());
    }

    @Autowired
    public void setUserMapper(UploadMapper uploadMapper) {
        this.uploadMapper = uploadMapper;
    }

    @Override
    public ControllerResponse store(MultipartFile file, HttpSession session) {
        String a = session.getAttribute("personName").toString();
        ControllerResponse checkFormatResponse = checkFormat(file);
        if (!checkFormatResponse.getABoolean()) {
            return checkFormatResponse;
        }
        if (getFormatName(file)) {
        } else {
            log.info("Maybe not a picture file!");
            return ControllerResponse.create("Maybe not a picture file!", false);
        }
        ControllerResponse copyResponse = copyPicture(file);
        if (copyResponse.getABoolean()) {
            return copyResponse;
        }

        ControllerResponse renamePictureResponse = renamePicture(file);
        if (renamePictureResponse.getABoolean()) {
            return renamePictureResponse;
        }
        String name = uploadMapper.findFile(session.getAttribute("uuid").toString()).getPictureName();
        if (name != null) {
            deleteFile(loadPicture(name));
        }
        ControllerResponse updatePictureResponse = updatePicture(renamePictureResponse.getMessage());
        return updatePictureResponse;
    }

    @Override
    public ControllerResponse storeFile(MultipartFile file, HttpSession session) {
        String a = session.getAttribute("uuid").toString();
        ControllerResponse checkFormatResponse = checkFormat(file);
        if (!checkFormatResponse.getABoolean()) {
            return checkFormatResponse;
        }
        ControllerResponse copyResponse = copyFile(file);
        if (copyResponse.getABoolean()) {
            return copyResponse;
        }
        ControllerResponse renameFileResponse = renameFile(file);
        if (renameFileResponse.getABoolean()) {
            return renameFileResponse;
        }
        String name = uploadMapper.findFile(session.getAttribute("uuid").toString()).getFileName();
        if (name != null) {
            deleteFile(loadFile(name));
        }
        ControllerResponse updateFileResponse = updateFile(renameFileResponse.getMessage());
        return updateFileResponse;
    }

    public ControllerResponse updatePicture(String fileName) {
        TpFile picture = new TpFile();
        picture.setPicturePath(uploadedPictureUrl(loadFile(fileName)).toString());
        picture.setPictureName(fileName);
        uploadMapper.updatePicture(picture);
        return ControllerResponse.create(picture.getPicturePath(), true);
    }

    public ControllerResponse updateFile(String fileName) {
        TpFile file = new TpFile();
        file.setFilePath(uploadedFileUrl(loadFile(fileName)).toString());
        file.setFileName(fileName);
        uploadMapper.updateFile(file);
        return ControllerResponse.create(file.getFilePath(), true);
    }

    public ControllerResponse renamePicture(MultipartFile file) {
        String temName = UUIDTool.getUuid() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        File oldPicture = new File("upload-dir" + File.separator + "picture" + File.separator + file.getOriginalFilename());
        File newPicture = new File("upload-dir" + File.separator + "picture" + File.separator + temName);
        try {
            boolean flag = oldPicture.renameTo(newPicture);
            if (flag) {
            } else {
                log.info("RenameToPicture faild！");
                return ControllerResponse.create("RenameToPicture faild！", true);
            }
        } catch (SecurityException e) {
            log.info("RenameToPicture faild！");
            return ControllerResponse.create("RenameToPicture faild！", true);
        }
        return ControllerResponse.create(temName, false);
    }

    public ControllerResponse renameFile(MultipartFile file) {
        String temName = UUIDTool.getUuid() + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        File oldFile = new File("upload-dir" + File.separator + "file" + File.separator + file.getOriginalFilename());
        File newFile = new File("upload-dir" + File.separator + "file" + File.separator + temName);
        try {
            boolean flag = oldFile.renameTo(newFile);
            if (flag) {
            } else {
                log.info("RenameToFile faild！");
                return ControllerResponse.create("RenameToFile faild！", true);
            }
        } catch (SecurityException e) {
            log.info("RenameToFile faild！");
            return ControllerResponse.create("RenameToFile faild！", true);
        }
        return ControllerResponse.create(temName, false);
    }

    public ControllerResponse copyPicture(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
        } catch (FileAlreadyExistsException e) {
            log.info("Picture already exist！");
            return ControllerResponse.create("Picture already exist！", true);
        } catch (IOException e) {
            log.info("Storage file failed！");
            return ControllerResponse.create("Storage file failed！ ", true);
        }
        log.info("success");
        return ControllerResponse.create("success", false);
    }

    public ControllerResponse copyFile(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), this.rootFileLocation.resolve(file.getOriginalFilename()));
        } catch (FileAlreadyExistsException e) {
            log.info("File already exist！");
            return ControllerResponse.create("File already exist！", true);
        } catch (IOException e) {
            log.info("Storage file failed！");
            return ControllerResponse.create("Storage file failed！", true);
        }
        log.info("success");
        return ControllerResponse.create("success", false);
    }

    public ControllerResponse checkFormat(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                log.info("文件(内容)为空！");
                return ControllerResponse.create("文件(内容)为空！", false);
            }
            String suffixName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            log.info("文件格式有误！");
            return ControllerResponse.create("文件格式有误！", false);
        }
        log.info("success");
        return ControllerResponse.create("success", true);
    }

    public URI uploadedPictureUrl(Path path) {
        UriComponents uriComponents = MvcUriComponentsBuilder
                .fromMethodName(FileUploadController.class, "servePicture", path.getFileName().toString()).build();
        return uriComponents.encode().toUri();
    }

    public URI uploadedFileUrl(Path path) {
        UriComponents uriComponents = MvcUriComponentsBuilder
                .fromMethodName(FileUploadController.class, "serveFile", path.getFileName().toString()).build();
        return uriComponents.encode().toUri();
    }

    @Override
    public Path loadPicture(String pictureName) {
        return rootLocation.resolve(pictureName);
    }

    @Override
    public Path loadFile(String fileName) {
        return rootFileLocation.resolve(fileName);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = loadPicture(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                log.info("Could not read picture: " + filename);
                throw new StorageFileNotFoundException("Could not read picture: " + filename);
            }
        } catch (MalformedURLException e) {
            log.info("Could not read picture:" + filename);
            throw new StorageFileNotFoundException("Could not read picture: " + filename, e);
        }

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
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
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
            Files.createDirectory(rootLocation);
            Files.createDirectory(rootFileLocation);
        } catch (IOException e) {
            log.info("初始化存储失败！");
            throw new StorageException("初始化存储失败！", e);
        }
    }

    public TpFile findFile(String name) {
        return uploadMapper.findFile(name);
    }

    public boolean getFormatName(MultipartFile file) {
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

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(path -> this.rootLocation.relativize(path));
        } catch (IOException e) {
            log.info("Failed to read the stored file！");
            throw new StorageException("Failed to read the stored file！", e);
        }
    }
}
