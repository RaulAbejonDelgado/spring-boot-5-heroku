package com.raul.spring.jpa.springjpav1.models.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

public interface IUploadService {

    public Resource load(String fileName) throws MalformedURLException;

    public String copy(MultipartFile photo) throws IOException;

    public boolean delete(String fileName);

    public void deleteAll();

    public void init() throws IOException;


}
