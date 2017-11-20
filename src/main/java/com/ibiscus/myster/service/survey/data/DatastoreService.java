package com.ibiscus.myster.service.survey.data;

import java.io.File;

public interface DatastoreService {

    String save(String tempFilePath);

    File get(String id);
}
