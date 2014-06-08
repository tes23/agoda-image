package com.agoda.image.calculator;

import com.agoda.image.calculator.downloader.BookingImageDownloader;
import com.agoda.image.calculator.downloader.ImageDownloader;
import junit.framework.Assert;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: tes
 * Date: 5/1/14
 * Time: 8:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class ImageDownloaderTest {
    private static final String BOOKING_URL = "http://www.booking.com/hotel/th/dusit-thani-laguna-phuket.en-gb.html?sid=cd3bba74a87896fbe06ee135486835e5;dcid=4;checkin=2014-11-07;checkout=2014-11-08;ucfs=1;srfid=bfe60b80e3c59d6d129110028fcf6034417be6f1X2";

    @Test
    public void testSelectDomELement() throws Exception {
        ImageDownloader downloader = new BookingImageDownloader();

        ImageData imageData = downloader.connectAndDownloadImages(BOOKING_URL, getDownloadDirectory());
        Assert.assertNotNull(imageData);
    }

    private String getDownloadDirectory() {
        return ImageDownloaderTest.class.getResource("/images").getPath();
    }
}
