package com.agoda.image.calculator;

import com.agoda.image.calculator.downloader.BookingImageDownloader;
import com.agoda.image.calculator.downloader.ImageDownloader;
import com.agoda.image.calculator.exceptions.AgodaImageException;
import com.agoda.image.calculator.manipulator.ImageManipulator;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static junit.framework.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: tes
 * Date: 5/1/14
 * Time: 8:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class BookingImageManipulatorTest {

    public static final String BOOKING_SAMPLE_URL = "http://www.booking.com/hotel/th/phi-phi-the-beach-resort.en-gb.html?sid=cd3bba74a87896fbe06ee135486835e5;dcid=4";

    @Test
    public void testSelectDomELement() throws Exception {
        ImageDownloader downloader = new BookingImageDownloader();
        ImageData imageData = downloader.connectAndDownloadImages(BOOKING_SAMPLE_URL, "src/test/resources/images");
        assertNotNull(imageData);

        for (FileData fileData : imageData.getFileDatas()) {
            System.out.println(fileData.getImageURL());
        }
    }

    @Test
    public void testDownloadAndResize() throws Exception {
        ImageDownloader downloader = new BookingImageDownloader();
        ImageData imageData = downloader.connectAndDownloadImages(BOOKING_SAMPLE_URL, "src/test/resources/images/resized");

        for (FileData fileData : imageData.getFileDatas()) {
            ImageManipulator manipulator = new ImageManipulator(fileData);
            manipulator.resize();
        }

    }

    @Test
    public void testGetAvailableImages() throws Exception {
        getAvailableImages();
    }

    private ImageData getAvailableImages() throws AgodaImageException {
        ImageDownloader downloader = new BookingImageDownloader();
        ImageData imageData = downloader.connectAndGetAvailableImages(BOOKING_SAMPLE_URL);

        assertNotNull(imageData);
        List<FileData> fileDatas = imageData.getFileDatas();
        printImageURLs(fileDatas, 45);

        return imageData;
    }

    @Test
    public void testManipulateImage() throws Exception {
//        File image = new File("src/test/resources/images/image_1024x684.jpg");
        File image = new File("src/test/resources/images/image_633x768.jpg");
        assertTrue(image.isFile());
        FileData fileData = new FileData("image_1024x684.jpg", image);
        ImageManipulator manipulator = new ImageManipulator(fileData);
        manipulator.resize();
    }

    private static void printImageURLs(List<FileData> fileDatas, int expectedSize) {
        assertNotNull(fileDatas);
        assertEquals(expectedSize, fileDatas.size());

        for (FileData fileData : fileDatas) {
            System.out.println(fileData.getImageURL());
        }
    }

    @Test
    public void testPreferredAspectRatioImage() throws Exception {
        FileData fileData = new FileData("27263909.jpg", new File("src\\test\\resources\\images\\resized\\27263909.jpg"));
        ImageManipulator manipulator = new ImageManipulator(fileData);
        manipulator.resize();
    }
}
