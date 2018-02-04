package com.ibiscus.myster.service.survey.data;

import com.google.cloud.storage.*;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import static java.lang.String.format;

public class GoogleDatastoreService implements DatastoreService {

    private static Storage storage;

    static {
        storage = StorageOptions.getDefaultInstance().getService();
    }

    private final String BUCKET_NAME = "myster";

    @Override
    public String save(String path, MultipartFile file) {
        BlobId blobId = BlobId.of(BUCKET_NAME, path + file.getOriginalFilename());
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();
        try {
            Blob blob = storage.create(blobInfo, IOUtils.toByteArray(file.getInputStream()));
            return blob.getName();
        } catch (IOException e) {
            String message = format("Cannot store file %s in the bucket %s", file.getOriginalFilename(),
                    BUCKET_NAME);
            throw new RuntimeException(message, e);
        }
    }

    @Override
    public String save(String tempFilePath) {
        return null;
    }

    @Override
    public File get(String id) {
        return null;
    }

    @Override
    public void delete(String fileUrl) {
        BlobId blobId = BlobId.of(BUCKET_NAME, fileUrl);
        if (!storage.delete(blobId)) {
            throw new IllegalArgumentException(format("Cannot delete %s file from datastore", fileUrl));
        }
    }
}
