package com.ibiscus.myster.web.shopper.survey;

import com.vaadin.data.HasValue;
import com.vaadin.server.FileResource;
import com.vaadin.shared.Registration;
import com.vaadin.ui.Image;
import com.vaadin.ui.Upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;

public class ImageUploader implements Upload.Receiver, Upload.SucceededListener, HasValue<String> {

    private final Image image;
    private Optional<File> file = Optional.empty();

    public ImageUploader(Image image) {
        this.image = image;
    }

    @Override
    public OutputStream receiveUpload(String filename, String mimeType) {
        OutputStream outputStream = null;
        try {
            file = Optional.of(File.createTempFile("temp", ".jpg"));
            outputStream = new FileOutputStream(file.get());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream;
    }

    @Override
    public void uploadSucceeded(Upload.SucceededEvent event) {
        image.setSource(new FileResource(file.get()));
    }

    @Override
    public void setValue(String value) {

    }

    @Override
    public String getValue() {
        if (file.isPresent()) {
            return file.get().getAbsolutePath();
        }
        return null;
    }

    @Override
    public Registration addValueChangeListener(ValueChangeListener<String> listener) {
        return null;
    }

    @Override
    public void setRequiredIndicatorVisible(boolean requiredIndicatorVisible) {

    }

    @Override
    public boolean isRequiredIndicatorVisible() {
        return false;
    }

    @Override
    public void setReadOnly(boolean readOnly) {

    }

    @Override
    public boolean isReadOnly() {
        return false;
    }
}
