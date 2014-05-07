package com.agoda.image.calculator;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: tes
 * Date: 5/2/14
 * Time: 7:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class FileData {
    private String imageURL;
    private File file;

    public FileData(String imageURL) {
        this.imageURL = imageURL;
    }

    public FileData(String imageURL, File file) {
        this.imageURL = imageURL;
        this.file = file;
    }

    public String getImageURL() {
        return imageURL;
    }

    public File getFile() {
        return file;
    }
}
