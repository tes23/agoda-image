package com.agoda.image.calculator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: tes
 * Date: 5/2/14
 * Time: 2:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class ImageData {

    private List<FileData> fileDatas;

    public ImageData() {
    }

    public ImageData(List<FileData> fileDataList) {
        this.fileDatas = fileDataList;
    }

    public void addFileData(FileData fileData1) {
        if(fileDatas == null) {
            fileDatas = new ArrayList<FileData>();
        }

        fileDatas.add(fileData1);
    }

    public List<FileData> getFileDatas() {
        return fileDatas;
    }
}
