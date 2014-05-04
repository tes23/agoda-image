package com.agoda.image.calculator;

import junit.framework.Assert;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created with IntelliJ IDEA.
 * User: tes
 * Date: 5/1/14
 * Time: 8:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class ImageDownloaderTest {

    @Test
    public void testSelectDomELement() throws Exception {
        ImageDownloader downloader = new BookingImageDownloader();
        ImageData imageData = downloader.connectAndDownload("http://www.booking.com/hotel/th/dusit-thani-laguna-phuket.en-gb.html?sid=cd3bba74a87896fbe06ee135486835e5;dcid=4;checkin=2014-11-07;checkout=2014-11-08;ucfs=1;srfid=bfe60b80e3c59d6d129110028fcf6034417be6f1X2", "agoda-imagecalculator/src/test/resources/images");
        Assert.assertNotNull(imageData);



    }
}
