package com.agoda.image.calculator;

import com.agoda.image.calculator.constants.AttributumSelector;
import com.agoda.image.calculator.constants.DOMSelector;
import com.agoda.image.calculator.exceptions.AgodaImageException;
import com.agoda.image.calculator.exceptions.ErrorCode;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: tes
 * Date: 5/2/14
 * Time: 2:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class BookingImageDownloader implements ImageDownloader {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookingImageDownloader.class);


    @Override
    public ImageData connectAndDownload(String URL, String destinationPath) throws AgodaImageException {
        try {
            Elements elements = getHTMLDocument(URL);

            return  downloadImages(elements, destinationPath);
        } catch (IOException e) {
            LOGGER.debug("HTML document can not be reached");
            throw new AgodaImageException(ErrorCode.UNABLE_TO_DOWNLOAD, e);
        }
    }

    private static Elements getHTMLDocument(String url) throws IOException {
        Document document = Jsoup.connect(url).get();
        return document.select(DOMSelector.BOOKING.getSelector());
    }

    private static ImageData downloadImages(Elements elements, String destinationPath) throws IOException {
        ImageData imageData = null;
        if(elements != null) {
            imageData = new ImageData();
            for (Element element : elements) {
                imageData.addFileData(downloadImage(element, destinationPath));
            }
        }
        return imageData;
    }

    private static FileData downloadImage(Element element, String destinationPath) throws IOException {
        final String imageURL = element.attr(AttributumSelector.BOOKING_IMAGE_URL.getSelector());
        Connection.Response imageResponse = Jsoup.connect(imageURL).ignoreContentType(true).execute();

        File file = new File(destinationPath + getFileName(imageURL));

        FileOutputStream outputStream = new FileOutputStream(file);
        outputStream.write(imageResponse.bodyAsBytes());
        outputStream.close();

        return new FileData(imageURL, file);
    }

    private static String getFileName(String imageURL) {
        return imageURL.substring(imageURL.lastIndexOf("/"));
    }
}
