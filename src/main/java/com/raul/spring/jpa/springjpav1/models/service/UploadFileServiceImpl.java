package com.raul.spring.jpa.springjpav1.models.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UploadFileServiceImpl  implements IUploadService{

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final String UPLOADS_PATH = "uploads";

    @Override
    public Resource load(String fileName) throws MalformedURLException{
        Path pathPhoto = getPath(fileName);
        log.info("pathPhoto: "+ pathPhoto);
        Resource resource = null;

        resource = new UrlResource(pathPhoto.toUri());
        if(!resource.exists() && ! resource.isReadable()) {
            throw new RuntimeException("Error: It is imposible" + pathPhoto.toString());
        }

        return resource;
    }

    @Override
    public String copy(MultipartFile photo) throws IOException {

        String uniqueFileName = UUID.randomUUID().toString()+"_"+photo.getOriginalFilename();
        Path rootPath = getPath(uniqueFileName);

        log.info("root path : "+ rootPath);

        try {

            Files.copy(photo.getInputStream(),rootPath);

        } catch (IOException e) {
            e.printStackTrace();
        }



        return uniqueFileName;
    }

    @Override
    public boolean delete(String fileName) {
        boolean res = false;
        Path rootPath = getPath(fileName);
        File file= rootPath.toFile();
        if(file.exists() && file.canRead()){
            if(file.delete()){
                res = true;
                log.info("The photo has been deleted success: -> "+ fileName);
            }
        }


        return res;
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(Paths.get(UPLOADS_PATH).toFile());
    }

    @Override
    public void init() throws IOException {
        Files.createDirectory(Paths.get(UPLOADS_PATH));
    }

    public Path getPath(String fileName){
        return Paths.get(UPLOADS_PATH).resolve(fileName).toAbsolutePath();
    }
}
