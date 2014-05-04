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
public class JSoupTest {

    @Test
    public void testSelectDomELement() throws Exception {
        Document document = Jsoup.connect("http://www.booking.com/hotel/th/phi-phi-the-beach-resort.en-gb.html?sid=cd3bba74a87896fbe06ee135486835e5;dcid=4").get();
        Elements elements = document.select("#photos_distinct a");
        Assert.assertNotNull(elements);

        for (Element element : elements) {
            String imageURL = element.attr("data-resized");
            System.out.println(imageURL);
            Connection.Response imageResponse = Jsoup.connect(imageURL).ignoreContentType(true).execute();

            String fileName = imageURL.substring(imageURL.lastIndexOf("/")+1);

            FileOutputStream outputStream = new FileOutputStream(new File("src/test/resources/images/" + fileName));
            outputStream.write(imageResponse.bodyAsBytes());
            outputStream.close();
        }

    }
}
