package com.ibiscus.myster.service.survey.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class LocalDataStore implements DatastoreService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocalDataStore.class);

    private final String filesLocation = "c:\\datastore\\";

    @Override
    public String save(String path, MultipartFile file) {
        File localFile = new File(filesLocation + path + file.getName());
        LOGGER.debug("Preparing to copy uploaded file {} to {} using local datastore", file.getName(),
                localFile.getAbsolutePath());
        try {
            FileOutputStream outputStream = new FileOutputStream(localFile);
            FileCopyUtils.copy(file.getInputStream(), outputStream);
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException("Cannot copy uploaded file " + file.getName() + " to "
                    + localFile.getAbsolutePath() + " using local datastore");
        }
        return file.getName();
    }

    @Override
    public String save(String tempFilePath) {
        return tempFilePath;
    }

    @Override
    public File get(String id) {
        return new File(filesLocation + id);
    }

    @Override
    public void delete(String path) {

    }

}
