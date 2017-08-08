package com.springboot.service.impl;

import com.springboot.controller.FileUploadController;
import com.springboot.domain.TpFile;
import com.springboot.dto.updto.ControllerResponse;
import com.springboot.exception.storage.StorageException;
import com.springboot.exception.storage.StorageFileNotFoundException;
import com.springboot.mapper.UploadMapper;
import com.springboot.service.StorageService;
import com.springboot.tools.UUIDTool;
import com.springboot.uploadDir.StorageProperties;
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

@Service
public class FileSystemStorageServiceImpl implements StorageService {

    private final Path rootLocation;
    private final Path rootFileLocation;
    private UploadMapper uploadMapper;


    @Autowired
    public FileSystemStorageServiceImpl(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
        this.rootFileLocation = Paths.get(properties.getFilelocation());
    }

    @Autowired
    public void setUserMapper(UploadMapper uploadMapper) {
        this.uploadMapper = uploadMapper;
    }

    @Override
    public ControllerResponse store(MultipartFile file) {

        ControllerResponse checkFormatResponse = checkFormat(file);
        if (!checkFormatResponse.getABoolean()) {
            return checkFormatResponse;
        }
        if (getFormatName(file)) {
        } else {
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
        String name = uploadMapper.findFile("张三").getPictureName();
        if (name != null) {
            deleteFile(loadPicture(name));
        }
        ControllerResponse updatePictureResponse = updatePicture(renamePictureResponse.getMessage());
        return updatePictureResponse;
    }

    @Override
    public ControllerResponse storeFile(MultipartFile file) {

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
        String name = uploadMapper.findFile("张三").getFileName();
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
        picture.setName("张三");
        uploadMapper.updatePicture(picture);
        return ControllerResponse.create(picture.getPicturePath(), true);
    }

    public ControllerResponse updateFile(String fileName) {
        TpFile file = new TpFile();
        file.setFilePath(uploadedFileUrl(loadFile(fileName)).toString());
        file.setFileName(fileName);
        file.setName("张三");
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
                return ControllerResponse.create("RenameToPicture faild！", true);
            }
        } catch (SecurityException e) {
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
                return ControllerResponse.create("RenameToFile faild！", true);
            }
        } catch (SecurityException e) {
            return ControllerResponse.create("RenameToFile faild！", true);
        }
        return ControllerResponse.create(temName, false);
    }

    public ControllerResponse copyPicture(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
        } catch (FileAlreadyExistsException e) {
            return ControllerResponse.create("Picture already exist！", true);
        } catch (IOException e) {
            return ControllerResponse.create("Storage file failed！ ", true);
        }
        return ControllerResponse.create("success", false);
    }

    public ControllerResponse copyFile(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), this.rootFileLocation.resolve(file.getOriginalFilename()));
        } catch (FileAlreadyExistsException e) {
            return ControllerResponse.create("File already exist！", true);
        } catch (IOException e) {
            return ControllerResponse.create("Storage file failed！ ", true);
        }
        return ControllerResponse.create("success", false);
    }

    public ControllerResponse checkFormat(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ControllerResponse.create("文件(内容)为空！", false);
            }
            String suffixName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            return ControllerResponse.create("文件格式有误！", false);
        }
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
                throw new StorageFileNotFoundException("Could not read picture: " + filename);
            }
        } catch (MalformedURLException e) {
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
                throw new StorageFileNotFoundException("Could not read picture: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read picture: " + filename, e);
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
        }
    }

    @Override
    public void init() {
        try {
            Files.createDirectory(rootLocation);
            Files.createDirectory(rootFileLocation);
        } catch (IOException e) {
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
            throw new StorageException("Failed to read the stored file！", e);
        }
    }
}
