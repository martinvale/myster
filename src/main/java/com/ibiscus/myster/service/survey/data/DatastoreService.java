package com.ibiscus.myster.service.survey.data;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface DatastoreService {

    String save(String path, MultipartFile file);

    String save(String tempFilePath);

    File get(String id);
}
