package com.agoda.image.calculator.manipulator;

import com.agoda.image.calculator.FileData;
import com.agoda.image.calculator.exceptions.AgodaImageException;
import com.agoda.image.calculator.exceptions.ErrorCode;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * User: acsikor
 * Date: 2014.05.07.
 * Time: 0:08
 */
public class ImageManipulator {

    private FileData fileData;

    public ImageManipulator(FileData fileData) {
        this.fileData = fileData;
    }

    public void resize() throws AgodaImageException {
        try {
            BufferedImage image = ImageIO.read(fileData.getFile());
//            int type = image.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : image.getType();
            resizeImage(image);
        } catch (IOException e) {
            throw new AgodaImageException(ErrorCode.IO_PROBLEM, e);
        }


    }

    private void resizeImage(BufferedImage originalImage) throws IOException {

//        1024 -> x  (912)
//        768  -> 684
        ImageInfo info = ImageSizeBuilder.calculate(originalImage.getWidth(), originalImage.getHeight());

        BufferedImage resizedImage = Scalr.crop(originalImage, info.x, info.y, info.width, info.height);
        ImageIO.write(resizedImage, "jpg", fileData.getFile());
    }

    private static class ImageInfo {
        private int x;
        private int y;
        private int width;
        private int height;
    }

    public static class ImageSizeBuilder {
        public static ImageInfo calculate(int originalWidth, int originalHeight) {
            ImageInfo imageSize = new ImageInfo();
            boolean isLandscapeLayout = originalWidth > originalHeight;
            if(isLandscapeLayout) {
                //for example: 1024 x 684
                imageSize.width = Double.valueOf(1024d/768d*originalHeight).intValue();
                imageSize.height = originalHeight;
                imageSize.x = (originalWidth - imageSize.width) / 2;
                imageSize.y = 0;
            } else {
                //for example: 633 x 768
                imageSize.width = originalWidth;
                imageSize.height = Double.valueOf(768d/1024d*originalWidth).intValue();
                imageSize.x = 0;
                imageSize.y = (originalHeight - imageSize.height) /2;
            }
            return imageSize;
        }
    }
}
