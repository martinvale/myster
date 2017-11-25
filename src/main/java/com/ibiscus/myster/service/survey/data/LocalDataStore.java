package com.ibiscus.myster.service.survey.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;

@Service
public class LocalDataStore implements DatastoreService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocalDataStore.class);

    private final String filesLocation = "c:\\datastore\\";

    @Override
    public String save(String tempFilePath) {
        File tempFile = new File(tempFilePath);
        File file = new File(filesLocation + tempFile.getName());
        LOGGER.debug("Preparing to copy temp file {} to {} using local datastore", tempFile.getAbsolutePath(),
                file.getAbsolutePath());
        try {
            FileCopyUtils.copy(tempFile, file);
        } catch (IOException e) {
            throw new RuntimeException("Cannot copy temp file " + tempFile.getAbsolutePath() + " to "
                    + file.getAbsolutePath() + " using local datastore");
        }
        return file.getName();
    }

    @Override
    public File get(String id) {
        return new File(filesLocation + id);
    }

}
