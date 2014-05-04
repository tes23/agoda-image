package com.agoda.image.calculator;

import com.agoda.image.calculator.exceptions.AgodaImageException;

/**
 * Created with IntelliJ IDEA.
 * User: tes
 * Date: 5/2/14
 * Time: 2:16 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ImageDownloader {

    ImageData connectAndDownload(String URL, String destinationPath) throws AgodaImageException;
}
