package com.agoda.image.calculator.manipulator;

import com.agoda.image.calculator.FileData;
import com.agoda.image.calculator.constants.GlobalConstants;
import com.agoda.image.calculator.exceptions.AgodaImageException;
import com.agoda.image.calculator.exceptions.ErrorCode;
import org.apache.commons.imaging.Imaging;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
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

//            BufferedImage image = ImageIO.read(fileData.getFile()); //ImageIO can't read CMYK-encoded images
              BufferedImage image = Imaging.getBufferedImage(fileData.getFile());
//            int type = image.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : image.getType();
            resizeImage(image);
        } catch (Exception e) {
            throw new AgodaImageException(ErrorCode.IO_PROBLEM, e);
        }


    }

    private static void createResizedDirectory(String absolutePath) {
        File resizedDirectory = new File(absolutePath);
        resizedDirectory.mkdir();

    }

    private void resizeImage(BufferedImage originalImage) throws IOException {

//        1024 -> x  (912)
//        768  -> 684
        ImageInfo info = ImageSizeBuilder.calculate(originalImage.getWidth(), originalImage.getHeight());

        BufferedImage resizedImage = Scalr.crop(originalImage, info.x, info.y, info.width, info.height);
        ImageIO.write(resizedImage, "jpg", createResizedFile());
    }

    private File createResizedFile() {
        String absolutePathFile = fileData.getFile().getAbsolutePath();
        int lastSeparatorIndex = absolutePathFile.lastIndexOf(File.separator);
        String absolutePath = absolutePathFile.substring(0, lastSeparatorIndex + 1) + GlobalConstants.RESIZED_DIRECTORY_NAME;

        createResizedDirectory(absolutePath);

        return new File(absolutePath + File.separator + absolutePathFile.substring(lastSeparatorIndex + 1));
    }

    private String getFileName() {
        return fileData.getFile().getName();
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
                imageSize.width = getPreferredSize(originalWidth, Double.valueOf(4d/3d*originalHeight).intValue());
                imageSize.height = originalHeight;
                int xCoord = (originalWidth - imageSize.width) / 2;
                imageSize.x = xCoord > 0 ? xCoord : 0;
                imageSize.y = 0;
            } else {
                //for example: 633 x 768
                imageSize.width = originalWidth;
                imageSize.height = getPreferredSize(originalHeight, Double.valueOf(3d/4d*originalWidth).intValue());
                imageSize.x = 0;
                int yCoord = (originalHeight - imageSize.height) /2;
                imageSize.y = yCoord > 0 ? yCoord : 0;
            }
            return imageSize;
        }

        private static int getPreferredSize(int originalSize, int newSize) {
            return newSize > originalSize ? originalSize : newSize;
        }
    }
}
