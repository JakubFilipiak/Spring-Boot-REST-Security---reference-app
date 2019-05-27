package com.jakubfilipiak.restthymeleaf.commons.storage;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Created by Jakub Filipiak on 10.05.2019.
 */
@Service
public class LocalFileService {

    private static final Logger logger =
            Logger.getLogger(LocalFileService.class.getName());

    private ServletContext servletContext;
    private String uploads;

    public LocalFileService(ServletContext servletContext) {
        this.servletContext = servletContext;
        createContextDirectory();
    }

    private void createContextDirectory() {
        uploads = servletContext.getRealPath("/uploads/");
        logger.log(Level.INFO, uploads);

        Path path = Paths.get(uploads);

        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<LocalFile> getResources() throws IOException {
        return Files.walk(Paths.get(uploads))
                .filter(Files::isRegularFile)
                .map(file -> {
                    try {
                        BasicFileAttributes attributes =
                                Files.readAttributes(file.toAbsolutePath(),
                                        BasicFileAttributes.class);

                        String downloadUri = ServletUriComponentsBuilder
                                .fromCurrentContextPath()
                                .path("/api/files/download/")
                                .path(file.getFileName().toString())
                                .toUriString();

                        String deleteUri = ServletUriComponentsBuilder
                                .fromCurrentContextPath()
                                .path("/api/files/delete/")
                                .path(file.getFileName().toString())
                                .toUriString();

                        return LocalFile.builder()
                                .name(file.getFileName().toString())
                                .creationTime(attributes.creationTime().toString())
                                .lastModified(attributes.lastModifiedTime().toString())
                                .size(attributes.size())
                                .downloadUri(downloadUri)
                                .deleteUri(deleteUri)
                                .fileType(Files.probeContentType(file.toAbsolutePath()))
                                .build();

                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .collect(Collectors.toList());
    }

    public void uploadFile(MultipartFile file) throws IOException {
        Path path = Paths.get(uploads + file.getOriginalFilename());
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
    }

    public File downloadFile(String fileName) {
        return new File(uploads + fileName);
    }

    public void deleteFile(String fileName) {
        File file = new File(uploads + fileName);

        if (file.exists()) {
            file.delete();
        }
    }

    public String getUploads() {
        return uploads;
    }
}
