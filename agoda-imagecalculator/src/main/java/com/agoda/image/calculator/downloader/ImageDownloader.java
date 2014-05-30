package com.agoda.image.calculator.downloader;

import com.agoda.image.calculator.ImageData;
import com.agoda.image.calculator.exceptions.AgodaImageException;

/**
 * Created with IntelliJ IDEA.
 * User: tes
 * Date: 5/2/14
 * Time: 2:16 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ImageDownloader {

    /**
     * Connect to the given page and retrieves the found images
     * @param url
     * @return
     * @throws AgodaImageException
     */
    ImageData connectAndGetAvailableImages(String url) throws AgodaImageException;

    /**
     * Connectto the given page and download the found images
     * @param url
     * @param destinationPath
     * @return
     * @throws AgodaImageException
     */
    ImageData connectAndDownloadImages(String url, String destinationPath) throws AgodaImageException;

    void downloadImages(ImageData imageData, String destinationPath) throws AgodaImageException;
}
